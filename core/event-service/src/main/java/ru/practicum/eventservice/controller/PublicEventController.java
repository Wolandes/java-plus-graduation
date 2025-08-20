package ru.practicum.eventservice.controller;

import ewm.client.CollectorClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.api.exception.eventservice.UserNotVisitedEventException;
import ru.practicum.api.exception.userservice.UserNotFoundException;
import ru.practicum.eventservice.model.EventSearch;
import ru.practicum.eventservice.service.EventService;
import ru.practicum.api.dto.eventservice.EventDto;
import ru.practicum.api.dto.eventservice.EventShortDto;
import ru.practicum.api.dto.eventservice.EventSort;
import ru.practicum.api.exception.eventservice.EventNotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@RequestMapping("/events")
@RequiredArgsConstructor
@RestController
@Slf4j
public class PublicEventController {
    private final EventService eventService;

    private final CollectorClient collectorClient;

    private final String USER_ID_HEADER = "X-EWM-USER-ID";

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
    }

    @GetMapping("/{eventId}")
    public EventDto getPublishedEventById(@RequestHeader(USER_ID_HEADER) Long userId, @PathVariable @Positive Long eventId) throws EventNotFoundException {
        log.info("Get published event with id = {}", eventId);
        collectorClient.sendPreviewEvent(userId, eventId);
        EventDto eventDto = eventService.getPublishedEventById(eventId);
        log.info("Создан Event");
        return eventDto;
    }

    @PutMapping("/{eventId}/like")
    @ResponseStatus(HttpStatus.OK)
    public void likeEvent(@RequestHeader(USER_ID_HEADER) Long userId, @PathVariable long id) throws EventNotFoundException, UserNotFoundException, UserNotVisitedEventException {
        eventService.checkUserRegistrationAtEvent(userId, id);
        collectorClient.sendLikeEvent(userId, id);
    }

    @GetMapping("/recommendations")
    @ResponseStatus(HttpStatus.OK)
    public List<EventDto> getRecommendationsForUser(@RequestHeader(USER_ID_HEADER) Long userId, @RequestParam int maxResults) {
        return eventService.getRecommendationsForUser(userId, maxResults);
    }
}
