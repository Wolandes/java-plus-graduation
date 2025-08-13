package ru.practicum.interactionapi.dto.eventservice;

/**
 * Изменение состояния объекта.
 */
public enum EventStateAction {
    /**
     * Отправить событие на проверку.
     */
    SEND_TO_REVIEW,

    /**
     * Отменить проверку события.
     */
    CANCEL_REVIEW,

    /**
     * Опубликовать событие.
     */
    PUBLISH_EVENT,

    /**
     * Отменить событие.
     */
    REJECT_EVENT
}
