package ru.practicum.requestservice.service;

import ru.practicum.interactionapi.dto.requestservice.RequestDto;
import ru.practicum.interactionapi.dto.requestservice.RequestsStatusDto;
import ru.practicum.interactionapi.dto.requestservice.UpdateEventRequestsStatusDto;
import ru.practicum.interactionapi.exception.eventservice.AccessToEventForbiddenException;
import ru.practicum.interactionapi.exception.eventservice.EventNotFoundException;
import ru.practicum.interactionapi.exception.requestservice.CancelRequestException;
import ru.practicum.interactionapi.exception.requestservice.CreateRequestException;
import ru.practicum.interactionapi.exception.requestservice.RequestNotFoundException;
import ru.practicum.interactionapi.exception.requestservice.UpdateRequestStatusException;
import ru.practicum.interactionapi.exception.userservice.UserNotFoundException;

import java.util.Collection;

public interface RequestService {
    RequestDto createRequest(Long requesterId, Long eventId) throws CreateRequestException, EventNotFoundException, UserNotFoundException;

    Collection<RequestDto> getUserRequests(Long requesterId);

    Collection<RequestDto> getEventRequests(Long initiatorId, Long eventId) throws AccessToEventForbiddenException, EventNotFoundException;

    RequestsStatusDto updateEventRequestsStatus(Long initiatorId, Long eventId, UpdateEventRequestsStatusDto updateEventRequestsStatusDto) throws AccessToEventForbiddenException, EventNotFoundException, UpdateRequestStatusException;

    RequestDto cancelUserRequest(Long requesterId, Long requestId) throws CancelRequestException, RequestNotFoundException;
}
