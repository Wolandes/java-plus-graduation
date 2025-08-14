package ru.practicum.eventservice.model;

import lombok.Builder;
import lombok.Data;
import ru.practicum.api.dto.eventservice.EventSort;
import ru.practicum.api.dto.eventservice.EventState;

import java.time.LocalDateTime;
import java.util.Collection;

@Builder(toBuilder = true)
@Data
public class EventSearch {
    private String text;

    private Collection<Long> users;

    private Collection<EventState> states;

    private Collection<Long> categories;

    private Boolean paid;

    private LocalDateTime rangeStart;

    private LocalDateTime rangeEnd;

    private boolean onlyAvailable;

    private EventSort sort;

    private int from;

    private int size;
}
