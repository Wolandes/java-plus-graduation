package ru.practicum.eventservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.eventservice.model.EventSearch;
import ru.practicum.eventservice.service.EventService;
import ru.practicum.interactionapi.dto.eventservice.EventDto;
import ru.practicum.interactionapi.dto.eventservice.EventState;
import ru.practicum.interactionapi.dto.eventservice.UpdateEventDto;
import ru.practicum.interactionapi.exception.categoryservice.CategoryNotFoundException;
import ru.practicum.interactionapi.exception.eventservice.EventEditingException;
import ru.practicum.interactionapi.exception.eventservice.EventNotFoundException;
import ru.practicum.interactionapi.exception.eventservice.InvalidEventDateException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@RequestMapping("/admin/events")
@RequiredArgsConstructor
@RestController
@Slf4j
public class AdminEventController {
    private final EventService eventService;

    @GetMapping
    public Collection<EventDto> getEvents(@RequestParam(required = false) Collection<Long> users,
                                          @RequestParam(required = false) Collection<EventState> states,
                                          @RequestParam(required = false) Collection<Long> categories,
                                          @RequestParam(required = false) String rangeStart,
                                          @RequestParam(required = false) String rangeEnd,
                                          @RequestParam(defaultValue = "0") int from,
                                          @RequestParam(defaultValue = "10") int size) {
        EventSearch eventSearch = EventSearch.builder()
                .users(users)
                .states(states)
                .categories(categories)
                .rangeStart(rangeStart != null ? LocalDateTime.parse(rangeStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null)
                .rangeEnd(rangeEnd != null ? LocalDateTime.parse(rangeEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null)
                .from(from)
                .size(size)
                .build();

        log.info("Get events with params {}", eventSearch);
        return eventService.getEvents(eventSearch);
    }

    @PatchMapping("/{eventId}")
    public EventDto updateEvent(@PathVariable @Positive Long eventId,
                                @RequestBody @Valid UpdateEventDto updateEventDto) throws CategoryNotFoundException, EventEditingException, EventNotFoundException, InvalidEventDateException {
        log.info("Update event {} with id={}", updateEventDto, eventId);
        return eventService.updateEvent(eventId, updateEventDto);
    }
}
