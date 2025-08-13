package ru.practicum.interactionapi.exception.requestservice;

/**
 * Исключение, выбрасываемое сервисом, если невозможно отменить заявку на участие в событии.
 */
public class CancelRequestException extends RuntimeException {
    /**
     * Конструктор.
     *
     * @param message сообщение, содержащее подробности исключения.
     */
    public CancelRequestException(String message) {
        super(message);
    }
}
