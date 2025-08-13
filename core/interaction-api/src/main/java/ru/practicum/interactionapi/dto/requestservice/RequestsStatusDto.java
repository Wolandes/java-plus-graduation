package ru.practicum.interactionapi.dto.requestservice;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

/**
 * Трансферный объект, содержащий данные по подтвержденным и отмененным заявкам на участие в событии.
 */
@Builder(toBuilder = true)
@Data
public class RequestsStatusDto {
    /**
     * Коллекция подтвержденных заявок.
     */
    Collection<RequestDto> confirmedRequests;

    /**
     * Коллекция отмененных заявок.
     */
    Collection<RequestDto> rejectedRequests;
}
