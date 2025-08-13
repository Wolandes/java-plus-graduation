package ru.practicum.interactionapi.exception.eventservice;

/**
 * Исключение, выбрасываемое сервисом, если доступ к событию запрещен.
 */
public class AccessToEventForbiddenException extends RuntimeException {
    /**
     * Конструктор.
     *
     * @param eventId идентификатор события.
     */
    public AccessToEventForbiddenException(Long eventId) {
        super(String.format("Доступ к событию с id=%d запрещён", eventId));
    }
}
