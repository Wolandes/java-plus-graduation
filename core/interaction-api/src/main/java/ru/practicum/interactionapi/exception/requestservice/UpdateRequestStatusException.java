package ru.practicum.interactionapi.exception.requestservice;

/**
 * Исключение, выбрасываемое сервисом, если невозможно обновить статус заявки на участие в событии.
 */
public class UpdateRequestStatusException extends RuntimeException {
    /**
     * Конструктор.
     *
     * @param message сообщение, содержащее подробности исключения.
     */
    public UpdateRequestStatusException(String message) {
        super(message);
    }
}
