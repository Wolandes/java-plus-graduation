package ru.practicum.event.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.service.EventService;
import ru.practicum.api.dto.event.CreateEventDto;
import ru.practicum.api.dto.event.EventDto;
import ru.practicum.api.dto.event.UpdateEventDto;

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
                                @RequestBody @Valid CreateEventDto createEventDto) {
        log.info("Create event - {} by user with id={}", createEventDto, initiatorId);
        return eventService.createEvent(initiatorId, createEventDto);
    }

    @GetMapping
    public Collection<EventDto> getEvents(@PathVariable(name = "userId") @Positive Long initiatorId,
                                          @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                          @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Get events for user with id={} from {}, size={}", initiatorId, from, size);
        return eventService.getEvents(initiatorId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventDto getEvent(@PathVariable(name = "userId") Long initiatorId,
                             @PathVariable @Positive Long eventId) {
        log.info("Get event with id = {} for user with id = {}", eventId, initiatorId);
        return eventService.getEvent(initiatorId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventDto updateEvent(@PathVariable(name = "userId") Long initiatorId,
                                @PathVariable @Positive Long eventId,
                                @RequestBody @Valid UpdateEventDto updateEventDto) {
        log.info("Update event {} with id = {} by user with id = {}", updateEventDto, eventId, initiatorId);
        return eventService.updateEvent(initiatorId, eventId, updateEventDto);
    }
}
