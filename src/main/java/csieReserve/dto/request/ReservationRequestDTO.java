package csieReserve.dto.request;

import csieReserve.domain.ReservationStatus;
import csieReserve.domain.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationRequestDTO {
    private Long userId;
    private String phoneNumber;
    private LocalDateTime reservationStartTime;
    private LocalDateTime reservationEndTime;
    private Integer participantCount;
    private LocalDateTime reservationDate;
    /**
     *  applicationDate, reservationStatus
     *  --> 이것들은 컨트롤러에서 값 넣어야 함
     * */
}
