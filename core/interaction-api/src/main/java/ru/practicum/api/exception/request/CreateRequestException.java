package ru.practicum.api.exception.request;

public class CreateRequestException extends RuntimeException {
    public CreateRequestException(String message) {
        super(message);
    }
}
