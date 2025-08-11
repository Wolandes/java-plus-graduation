package ru.practicum.event.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.event.mapper.EventStateConverter;
import ru.practicum.event.mapper.LocationConverter;
import ru.practicum.api.dto.event.EventState;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "events", schema = "public")
@ToString
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "initiator_id", nullable = false)
    private Long initiatorId;

    @Column(name = "title", nullable = false, length = 120)
    private String title;

    @Column(name = "annotation", nullable = false, length = 2000)
    private String annotation;

    @Column(name = "description", nullable = false, length = 7000)
    private String description;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "location", nullable = false)
    @Convert(converter = LocationConverter.class)
    private Location location;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Column(name = "paid")
    private boolean paid;

    @Column(name = "participant_limit")
    private int participantLimit;

    @Column(name = "request_moderation")
    private boolean requestModeration;

    @Column(name = "confirmed_requests")
    private int confirmedRequests;

    @Column(name = "state")
    @Convert(converter = EventStateConverter.class)
    private EventState state;
}
