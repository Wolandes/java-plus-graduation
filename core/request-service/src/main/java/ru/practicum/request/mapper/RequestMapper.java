package ru.practicum.request.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.api.dto.request.RequestDto;
import ru.practicum.request.model.Request;

import java.util.Collection;

@Component
public class RequestMapper {
    public RequestDto mapToRequestDto(Request request) {
        return RequestDto.builder()
                .id(request.getId())
                .created(request.getCreated())
                .event(request.getEventId())
                .requester(request.getRequesterId())
                .status(request.getStatus())
                .build();
    }

    public Collection<RequestDto> mapToRequestDtoCollection(Collection<Request> requests) {
        return requests.stream().map(this::mapToRequestDto).toList();
    }
}
