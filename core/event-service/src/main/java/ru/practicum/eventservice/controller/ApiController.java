package ru.practicum.eventservice.controller;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.eventservice.service.EventService;
import ru.practicum.interactionapi.dto.eventservice.EventDto;
import ru.practicum.interactionapi.dto.eventservice.EventShortDto;
import ru.practicum.interactionapi.exception.eventservice.EventNotFoundException;

import java.util.Collection;

@RequestMapping("/interaction/events")
@RequiredArgsConstructor
@RestController
@Slf4j
public class ApiController {
    private final EventService eventService;

    @GetMapping
    public Collection<EventShortDto> getEvents(@RequestParam(name = "ids") Collection<Long> eventIds) {
        log.info("Get events with ids = {}", eventIds);
        return eventService.getEvents(eventIds);
    }

    @GetMapping("/{eventId}")
    public EventDto getEvent(@PathVariable @Positive Long eventId) throws EventNotFoundException {
        log.info("Get event with id={}", eventId);
        return eventService.getPublishedEventById(eventId);
    }

    @GetMapping("/check/existence/by/id/{eventId}")
    public boolean isEventExists(@PathVariable Long eventId) {
        log.info("Check existence of event with id={}", eventId);
        return eventService.isEventExists(eventId);
    }

    @GetMapping("/check/existence/with/category/{categoryId}")
    public boolean isEventsWithCategoryExists(@PathVariable Long categoryId) {
        log.info("Check existence of event with category with id={}", categoryId);
        return eventService.isEventsWithCategoryExists(categoryId);
    }

    @GetMapping("/check/publication/{eventId}")
    public boolean isEventPublished(@PathVariable Long eventId) throws EventNotFoundException {
        log.info("Check publication of event with id={}", eventId);
        return eventService.isEventPublished(eventId);
    }

    @PatchMapping("/{eventId}/participation/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmParticipation(@PathVariable @Positive Long eventId) throws EventNotFoundException {
        log.info("Confirm participation for event with id={}", eventId);
        eventService.confirmParticipation(eventId);
    }

    @PatchMapping("/{eventId}/participation/reject")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rejectParticipation(@PathVariable @Positive Long eventId) throws EventNotFoundException {
        log.info("Reject participation for event with id={}", eventId);
        eventService.rejectParticipation(eventId);
    }
}
