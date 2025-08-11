package ru.practicum.api.exception.event;

public class AccessToEventForbiddenException extends RuntimeException {
    public AccessToEventForbiddenException(Long eventId) {
        super(String.format("Доступ к событию запрещён с id: " + eventId));
    }
}
