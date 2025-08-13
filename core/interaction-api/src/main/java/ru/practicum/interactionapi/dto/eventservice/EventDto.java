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
 * Трансферный объект, содержащий данные о событии.
 */
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
public class EventDto {
    /**
     * Уникальный идентификатор события.
     */
    private Long id;

    /**
     * Дата и время создания события.
     */
    private LocalDateTime createdOn;

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
     * Полное описание события.
     */
    private String description;

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
     * Широта и долгота места проведения события.
     */
    private LocationDto location;

    /**
     * Дата и время публикации события.
     */
    private LocalDateTime publishedOn;

    /**
     * Признак, нужно ли оплачивать событие.
     */
    private boolean paid;

    /**
     * Ограничение на количество участников.
     * 0 - означает отсутствие ограничения.
     */
    private int participantLimit;

    /**
     * Признак, нужна ли пре-модерация заявок на участие.
     * Если true, то все заявки будут ожидать подтверждения инициатором события. Если false - то будут подтверждаться автоматически.
     */
    private boolean requestModeration;

    /**
     * Количество одобренных заявок на участие в данном событии.
     */
    private int confirmedRequests;

    /**
     * Состояние события.
     */
    private EventState state;

    /**
     * Количество просмотров события.
     */
    private long views;
}
