package ru.practicum.api.exception.request;

public class UpdateRequestStatusException extends RuntimeException {
    public UpdateRequestStatusException(String message) {
        super(message);
    }
}
