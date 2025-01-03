package csieReserve.controller;

import csieReserve.Repository.UserRepository;
import csieReserve.dto.request.ReservationRequestDTO;
import csieReserve.dto.response.ApiResponse;
import csieReserve.dto.response.ReservationResponseDTO;
import csieReserve.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReservationController {

    private final ReservationService reservationService;
    private final UserRepository userRepository;

    @Operation(summary = "해당 유저의 전체 회의실 예약 내역 조회 API",
            description = "DB에 저장된 해당 유저의 전체 회의실 예약 내역 조회 API")
    @GetMapping("/reservation/get/{id}")
    public ResponseEntity<ApiResponse<List<ReservationResponseDTO>>> getUserReservations(@PathVariable Long id){
        return ResponseEntity.ok(new ApiResponse<>("해당 유저의 모든 예약 내역 조회 완료",
                reservationService.getUserReservations(id)));
    }

    @Operation(summary = "회의실 예약 생성 API",
            description = "회의실 예약 생성 및 확인 문자 자동 발송 API")
    @PostMapping("/reservation/create")
    public ResponseEntity<ApiResponse<ReservationResponseDTO>> createReservation(@RequestBody ReservationRequestDTO requestDTO) throws SchedulerException {
        return ResponseEntity.ok(new ApiResponse<>("성공적으로 예약 저장 완료 및 예약 확인 문자 발송",
                reservationService.createReservation(requestDTO)));
    }

    @Operation(summary = "회의실 예약 취소 API",
            description = "회의실 예약 취소 및 예약 상태 변경 API")
    @PutMapping("/reservation/cancel/{id}")
    public ResponseEntity<ApiResponse<ReservationResponseDTO>> deleteReservation(@PathVariable Long id) throws SchedulerException {
        return ResponseEntity.ok(new ApiResponse<>("성공적으로 예약을 취소 하였습니다",
                reservationService.cancelReservation(id)));
    }


}
