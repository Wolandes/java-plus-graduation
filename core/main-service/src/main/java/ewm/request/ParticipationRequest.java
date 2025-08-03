package ewm.request;

import ewm.event.Event;
import ewm.user.User;
import lombok.*;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "participation_requests")
@ToString
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @JoinColumn(name = "event_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Event event;

    @JoinColumn(name = "requester_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User requester;

    @Column(name = "status", nullable = false)
    @Convert(converter = ParticipationRequestStatusConverter.class)
    private ParticipationRequestStatus status;
}
