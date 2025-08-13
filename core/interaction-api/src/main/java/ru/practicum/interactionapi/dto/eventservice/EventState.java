package ru.practicum.interactionapi.dto.eventservice;

/**
 * Состояние события.
 */
public enum EventState {
    /**
     * В состоянии ожидания.
     */
    PENDING,

    /**
     * Опубликовано.
     */
    PUBLISHED,

    /**
     * Отклонено.
     */
    REJECTED,

    /**
     * Отменено.
     */
    CANCELED
}
