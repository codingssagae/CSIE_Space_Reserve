package csieReserve.mapper;

import csieReserve.Repository.UserRepository;
import csieReserve.domain.Reservation;
import csieReserve.domain.User;
import csieReserve.dto.request.ReservationRequestDTO;
import csieReserve.dto.response.ReservationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReservationMapper {

    private final UserRepository userRepository;
    public Reservation toEntity(ReservationRequestDTO dto){
        Optional<User> byId = userRepository.findById(dto.getUserId());
        return Reservation.builder()
                .user(byId.get())
                .phoneNumber(dto.getPhoneNumber())
                .reservationStartTime(dto.getReservationStartTime())
                .reservationEndTime(dto.getReservationEndTime())
                .participantCount(dto.getParticipantCount())
                .reservationDate(dto.getReservationDate())
                .build();
    }

    public ReservationResponseDTO toDTO(Reservation reservation){
        return ReservationResponseDTO.builder()
                .userId(reservation.getUser().getId())
                .phoneNumber(reservation.getPhoneNumber())
                .reservationStartTime(reservation.getReservationStartTime())
                .reservationEndTime(reservation.getReservationEndTime())
                .participantCount(reservation.getParticipantCount())
                .reservationDate(reservation.getReservationDate())
                .applicationDate(reservation.getApplicationDate())
                .reservationStatus(reservation.getReservationStatus())
                .build();
    }

}
