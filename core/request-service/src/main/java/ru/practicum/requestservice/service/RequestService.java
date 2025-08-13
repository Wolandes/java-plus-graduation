package ru.practicum.requestservice.service;

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

import java.util.Collection;

public interface RequestService {
    RequestDto createRequest(Long requesterId, Long eventId) throws CreateRequestException, EventNotFoundException, UserNotFoundException;

    Collection<RequestDto> getUserRequests(Long requesterId);

    Collection<RequestDto> getEventRequests(Long initiatorId, Long eventId) throws AccessToEventForbiddenException, EventNotFoundException;

    RequestsStatusDto updateEventRequestsStatus(Long initiatorId, Long eventId, UpdateEventRequestsStatusDto updateEventRequestsStatusDto) throws AccessToEventForbiddenException, EventNotFoundException, UpdateRequestStatusException;

    RequestDto cancelUserRequest(Long requesterId, Long requestId) throws CancelRequestException, RequestNotFoundException;
}
