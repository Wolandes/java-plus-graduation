package ru.practicum.api.exception.eventservice;

public class UserNotVisitedEventException extends RuntimeException {
    public UserNotVisitedEventException(String message) {
        super(message);
    }
}
