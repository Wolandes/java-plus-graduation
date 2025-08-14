package ru.practicum.requestservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.api.dto.requestservice.RequestDto;
import ru.practicum.api.dto.requestservice.RequestsStatusDto;
import ru.practicum.api.dto.requestservice.UpdateEventRequestsStatusDto;
import ru.practicum.api.exception.eventservice.AccessToEventForbiddenException;
import ru.practicum.api.exception.eventservice.EventNotFoundException;
import ru.practicum.api.exception.requestservice.CancelRequestException;
import ru.practicum.api.exception.requestservice.CreateRequestException;
import ru.practicum.api.exception.requestservice.RequestNotFoundException;
import ru.practicum.api.exception.requestservice.UpdateRequestStatusException;
import ru.practicum.api.exception.userservice.UserNotFoundException;
import ru.practicum.requestservice.service.RequestService;

import java.util.Collection;

@RequestMapping("/users/{userId}")
@RequiredArgsConstructor
@RestController
@Slf4j
public class RequestController {
    private final RequestService requestService;

    @PostMapping("/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto createRequest(@PathVariable(name = "userId") @Positive Long requesterId,
                                    @RequestParam @Positive Long eventId) throws CreateRequestException, EventNotFoundException, UserNotFoundException {
        log.info("Create request for event with id={} by user with id={}", eventId, requesterId);
        return requestService.createRequest(requesterId, eventId);
    }

    @GetMapping("/requests")
    @ResponseStatus(HttpStatus.OK)
    public Collection<RequestDto> getUserRequests(@PathVariable(name = "userId") @Positive Long requesterId) {
        log.info("Get requests for user with id={}", requesterId);
        return requestService.getUserRequests(requesterId);
    }

    @GetMapping("/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public Collection<RequestDto> getEventRequests(@PathVariable(name = "userId") @Positive Long initiatorId,
                                                   @PathVariable @Positive Long eventId) throws AccessToEventForbiddenException, EventNotFoundException {
        log.info("Get requests for event with id={} initiated by user with id={}", eventId, initiatorId);
        return requestService.getEventRequests(initiatorId, eventId);
    }

    @PatchMapping("/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public RequestsStatusDto updateEventRequestsStatus(@PathVariable(name = "userId") @Positive Long initiatorId,
                                                       @PathVariable @Positive Long eventId,
                                                       @RequestBody @Valid UpdateEventRequestsStatusDto updateEventRequestsStatusDto) throws AccessToEventForbiddenException, EventNotFoundException, UpdateRequestStatusException {
        log.info("Update participation request status {} for event with id={} and user with id={}", updateEventRequestsStatusDto, initiatorId, eventId);
        return requestService.updateEventRequestsStatus(initiatorId, eventId, updateEventRequestsStatusDto);
    }

    @PatchMapping("/requests/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable(name = "userId") @Positive Long requesterId,
                                    @PathVariable @Positive Long requestId) throws CancelRequestException, RequestNotFoundException {
        log.info("Cancel request with id={} for user with id={}", requestId, requesterId);
        return requestService.cancelUserRequest(requesterId, requestId);
    }
}
