package csieReserve.service;

import csieReserve.Repository.ReservationRepository;
import csieReserve.domain.Reservation;
import csieReserve.domain.ReservationStatus;
import csieReserve.dto.request.ReservationRequestDTO;
import csieReserve.dto.response.ReservationResponseDTO;
import csieReserve.job.SendReservationReminderJob;
import csieReserve.mapper.ReservationMapper;
import csieReserve.util.CoolSmsUtil;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static csieReserve.domain.ReservationStatus.*;
import static java.time.LocalDateTime.*;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final Scheduler scheduler;
    private final CoolSmsUtil smsUtil;
    private final ReservationMapper reservationMapper;

    /**
     * 예약 생성
     * -> 예약 생성 시 자동으로 예약자 번호로 확인 문자 전송
     * -> 예약 10분 전 확인 문자 Quartz 스케쥴링을 통해 예약 전송
     * */
    @Transactional
    public ReservationResponseDTO createReservation(ReservationRequestDTO requestDTO) throws SchedulerException{
        Reservation reservation = reservationMapper.toEntity(requestDTO);
        reservation.setApplicationDate(now());
        reservation.setReservationStatus(RESERVED);

        reservationRepository.save(reservation); // 예약 저장

        // 예약 성공 문자 발송하기
        String successMessage = "[가톨릭대학교 컴퓨터정보공학부 회의실] 예약이 성공적으로 완료되었습니다.\n예약 시간: "
                + reservation.getReservationStartTime() + " ~ " + reservation.getReservationEndTime();
        smsUtil.sendSMS(reservation.getPhoneNumber(), successMessage);

        // 예약 10분 전 확인 문자 보내기
        Date reminderTime = Date.from(reservation.getReservationStartTime()
                .minusMinutes(10)
                .atZone(ZoneId.systemDefault())
                .toInstant());

        if (reminderTime.after(new Date())){
            JobDetail reminderJob = JobBuilder.newJob(SendReservationReminderJob.class)
                    .withIdentity("reminderJob_"+reservation.getId(),"reservationJobs")
                    .usingJobData("reservationId", reservation.getId())
                    .build();

            Trigger reminderTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("reminderTrigger_"+reservation.getId(),"reservationTriggers")
                    .startAt(reminderTime)
                    .build();

            scheduler.scheduleJob(reminderJob,reminderTrigger);
        }

        return reservationMapper.toDTO(reservation);
    }

    /**
     * 예약 취소
     *
     * */
    @Transactional
    public ReservationResponseDTO cancelReservation(Long reservationId) throws SchedulerException{
        Optional<Reservation> repositoryById = reservationRepository.findById(reservationId);
        Reservation reservation = repositoryById.get();
        if(reservation !=null && reservation.getReservationStatus()==RESERVED){
            reservation.setReservationStatus(CANCELLED);
            reservationRepository.save(reservation);

            // Quartz 스케쥴링 된 Job 제거
            scheduler.deleteJob(JobKey.jobKey("reminderJob_"+reservationId+"reminderJobs"));
            scheduler.deleteJob(JobKey.jobKey("reminderTrigger_"+reservationId+"reminderTriggers"));
        }
        return reservationMapper.toDTO(reservation);
    }

    /**
     * 예약자 고유 id를 가져와 모든 예약을 조회
     *
     * */
    public List<ReservationResponseDTO> getUserReservations(Long userId){
        return reservationRepository.findByUserId(userId).stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }
}
