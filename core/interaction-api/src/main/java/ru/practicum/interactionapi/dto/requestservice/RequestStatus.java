package ru.practicum.interactionapi.dto.requestservice;

/**
 * Статус заявки на участие в событии.
 */
public enum RequestStatus {
    /**
     * В ожидании.
     */
    PENDING,

    /**
     * Подтверждена.
     */
    CONFIRMED,

    /**
     * Отменено.
     */
    CANCELED,

    /**
     * Отклонена.
     */
    REJECTED
}
