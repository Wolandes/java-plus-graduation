package ru.practicum.eventservice.controller;

import ewm.CreateEndpointHitDto;
import ewm.client.StatsClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.eventservice.model.EventSearch;
import ru.practicum.eventservice.service.EventService;
import ru.practicum.api.dto.eventservice.EventDto;
import ru.practicum.api.dto.eventservice.EventShortDto;
import ru.practicum.api.dto.eventservice.EventSort;
import ru.practicum.api.exception.eventservice.EventNotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@RequestMapping("/events")
@RequiredArgsConstructor
@RestController
@Slf4j
public class PublicEventController {
    private final EventService eventService;

    private final StatsClient statsClient;

    @GetMapping
    public Collection<EventShortDto> getEvents(@RequestParam(required = false) String text,
                                               @RequestParam(required = false) Collection<@Positive Long> categories,
                                               @RequestParam(required = false) Boolean paid,
                                               @RequestParam(required = false) String rangeStart,
                                               @RequestParam(required = false) String rangeEnd,
                                               @RequestParam(defaultValue = "false") boolean onlyAvailable,
                                               @RequestParam(required = false) EventSort sort,
                                               @RequestParam(defaultValue = "0") int from,
                                               @RequestParam(defaultValue = "10") int size,
                                               HttpServletRequest request) {
        try {
            EventSearch eventSearch = EventSearch.builder()
                    .text(text)
                    .categories(categories)
                    .paid(paid)
                    .rangeStart(rangeStart != null ? LocalDateTime.parse(rangeStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null)
                    .rangeEnd(rangeEnd != null ? LocalDateTime.parse(rangeEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null)
                    .onlyAvailable(onlyAvailable)
                    .sort(sort)
                    .from(from)
                    .size(size)
                    .build();

            log.info("Get events with params {}", eventSearch);
            return eventService.getPublishedEvents(eventSearch);
        } finally {
            try {
                statsClient.sendHit(new CreateEndpointHitDto("event-service", request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }
    }

    @GetMapping("/{eventId}")
    public EventDto getPublishedEventById(@PathVariable @Positive Long eventId, HttpServletRequest request) throws EventNotFoundException {
        log.info("Get published event with id = {}", eventId);

        try {
            return eventService.getPublishedEventById(eventId);
        } finally {
            try {
                statsClient.sendHit(new CreateEndpointHitDto("event-service", request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }
    }
}
