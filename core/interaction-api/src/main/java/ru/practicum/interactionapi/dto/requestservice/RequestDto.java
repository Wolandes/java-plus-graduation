package ru.practicum.interactionapi.dto.requestservice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Трансферный объект, содержащий данные о заявке на участие в событии.
 */
@Builder(toBuilder = true)
@Data
public class RequestDto {
    /**
     * Идентификатор заявки.
     */
    private Long id;

    /**
     * Дата создания заявки.
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime created;

    /**
     * Идентификатор события.
     */
    private Long event;

    /**
     * Идентификатор пользователя, оставившего заявку.
     */
    private Long requester;

    /**
     * Статус заявки.
     */
    private RequestStatus status;
}
