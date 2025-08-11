package ru.practicum.request.service;

import ru.practicum.api.dto.request.RequestDto;
import ru.practicum.api.dto.request.RequestsStatusDto;
import ru.practicum.api.dto.request.UpdateEventRequestsStatusDto;

import java.util.Collection;

public interface RequestService {
    RequestDto createRequest(Long requesterId, Long eventId);

    Collection<RequestDto> getUserRequests(Long requesterId);

    Collection<RequestDto> getEventRequests(Long initiatorId, Long eventId);

    RequestsStatusDto updateEventRequestsStatus(Long initiatorId, Long eventId, UpdateEventRequestsStatusDto updateEventRequestsStatusDto);

    RequestDto cancelUserRequest(Long requesterId, Long requestId);
}
