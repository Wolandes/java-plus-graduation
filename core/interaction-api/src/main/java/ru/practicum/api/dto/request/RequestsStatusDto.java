package ru.practicum.api.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Builder(toBuilder = true)
@Data
public class RequestsStatusDto {
    Collection<RequestDto> confirmedRequests;

    Collection<RequestDto> rejectedRequests;
}
