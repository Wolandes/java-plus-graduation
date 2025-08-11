package ru.practicum.api.exception.request;

public class RequestNotFoundException extends RuntimeException {
    public RequestNotFoundException(long requestId) {
        super(String.format("Заявка с id=%d не найдена", requestId));
    }
}
