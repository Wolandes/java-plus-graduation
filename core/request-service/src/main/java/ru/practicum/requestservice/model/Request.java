package ru.practicum.requestservice.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.interactionapi.dto.requestservice.RequestStatus;
import ru.practicum.requestservice.mapper.converter.RequestStatusConverter;

import java.time.LocalDateTime;

/**
 * Заявка на участие в событии.
 */
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "requests")
@ToString
public class Request {
    /**
     * Идентификатор заявки.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Дата создания заявки.
     */
    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    /**
     * Идентификатор события.
     */
    @Column(name = "event_id", nullable = false)
    private Long eventId;

    /**
     * Идентификатор пользователя, оставившего заявку.
     */
    @Column(name = "requester_id", nullable = false)
    private Long requesterId;

    /**
     * Статус заявки.
     */
    @Column(name = "status", nullable = false)
    @Convert(converter = RequestStatusConverter.class)
    private RequestStatus status;
}
