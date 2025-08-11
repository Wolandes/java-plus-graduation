package ru.practicum.api.exception.request;

public class CancelRequestException extends RuntimeException {
    public CancelRequestException(String message) {
        super(message);
    }
}
