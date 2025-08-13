package ru.practicum.interactionapi.dto.eventservice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.interactionapi.dto.categoryservice.CategoryDto;
import ru.practicum.interactionapi.dto.userservice.UserDto;

import java.time.LocalDateTime;

/**
 * Краткая информация о событии.
 */
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
public class EventShortDto {
    /**
     * Уникальный идентификатор события.
     */
    private Long id;

    /**
     * Инициатор события.
     */
    private UserDto initiator;

    /**
     * Заголовок события.
     */
    private String title;

    /**
     * Краткое описание события.
     */
    private String annotation;

    /**
     * Дата и время на которые намечено событие.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;

    /**
     * Категория события.
     */
    private CategoryDto category;

    /**
     * Признак, нужно ли оплачивать событие.
     */
    private boolean paid;

    /**
     * Количество одобренных заявок на участие в данном событии.
     */
    private int confirmedRequests;

    /**
     * Количество просмотров события.
     */
    private long views;
}
