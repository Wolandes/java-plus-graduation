package ru.practicum.eventservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.eventservice.service.EventService;
import ru.practicum.api.dto.eventservice.CreateEventDto;
import ru.practicum.api.dto.eventservice.EventDto;
import ru.practicum.api.dto.eventservice.UpdateEventDto;
import ru.practicum.api.exception.categoryservice.CategoryNotFoundException;
import ru.practicum.api.exception.eventservice.AccessToEventForbiddenException;
import ru.practicum.api.exception.eventservice.EventEditingException;
import ru.practicum.api.exception.eventservice.EventNotFoundException;
import ru.practicum.api.exception.eventservice.InvalidEventDateException;
import ru.practicum.api.exception.userservice.UserNotFoundException;

import java.util.Collection;

@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@RestController
@Slf4j
public class UserEventController {
    private final EventService eventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto createEvent(@PathVariable(name = "userId") @Positive Long initiatorId,
                                @RequestBody @Valid CreateEventDto createEventDto) throws InvalidEventDateException {
        log.info("Create event - {} by user with id={}", createEventDto, initiatorId);
        return eventService.createEvent(initiatorId, createEventDto);
    }

    @GetMapping
    public Collection<EventDto> getEvents(@PathVariable(name = "userId") @Positive Long initiatorId,
                                          @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                          @RequestParam(defaultValue = "10") @Positive int size) throws UserNotFoundException {
        log.info("Get events for user with id={} from {}, size={}", initiatorId, from, size);
        return eventService.getEvents(initiatorId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventDto getEvent(@PathVariable(name = "userId") Long initiatorId,
                             @PathVariable @Positive Long eventId) throws AccessToEventForbiddenException, EventNotFoundException {
        log.info("Get event with id = {} for user with id = {}", eventId, initiatorId);
        return eventService.getEvent(initiatorId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventDto updateEvent(@PathVariable(name = "userId") Long initiatorId,
                                @PathVariable @Positive Long eventId,
                                @RequestBody @Valid UpdateEventDto updateEventDto) throws AccessToEventForbiddenException, CategoryNotFoundException, EventEditingException, EventNotFoundException, InvalidEventDateException {
        log.info("Update event {} with id = {} by user with id = {}", updateEventDto, eventId, initiatorId);
        return eventService.updateEvent(initiatorId, eventId, updateEventDto);
    }
}
