package ru.practicum.api.exception.event;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(long eventId) {
        super(String.format("Событие не найдено с id: " + eventId));
    }
}
