package ru.practicum.api.exception.event;

public class EventEditingException extends RuntimeException {
    public EventEditingException(long eventId) {
        super(String.format("Событие запрещено для редактирования с id: " + eventId));
    }

    public EventEditingException(String message) {
        super(message);
    }
}