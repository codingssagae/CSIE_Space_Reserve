package csieReserve.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String phoneNumber;

    private LocalDateTime reservationStartTime;

    private LocalDateTime reservationEndTime;

    private Integer participantCount;

    private LocalDateTime reservationDate;

    private LocalDateTime applicationDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

}
