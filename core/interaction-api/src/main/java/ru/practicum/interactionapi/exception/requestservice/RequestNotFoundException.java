package ru.practicum.interactionapi.exception.requestservice;

/**
 * Исключение, выбрасываемое сервисом, если заявка на участие в событие не была найдена.
 */
public class RequestNotFoundException extends RuntimeException {
    /**
     * Конструктор.
     *
     * @param requestId идентификатор заявки.
     */
    public RequestNotFoundException(long requestId) {
        super(String.format("Заявка с id=%d не найдена", requestId));
    }
}
