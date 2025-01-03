package csieReserve.dto.response;

import csieReserve.domain.ReservationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReservationResponseDTO {
    private Long id;
    private Long userId;
    private String phoneNumber;
    private LocalDateTime reservationStartTime;
    private LocalDateTime reservationEndTime;
    private Integer participantCount;
    private LocalDateTime reservationDate;
    private LocalDateTime applicationDate;
    private ReservationStatus reservationStatus;
}
