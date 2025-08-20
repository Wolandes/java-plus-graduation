package ru.practicum.api.exception;

public class RemoteServiceException extends RuntimeException {
    public RemoteServiceException(String message) {
        super(message);
    }
}
