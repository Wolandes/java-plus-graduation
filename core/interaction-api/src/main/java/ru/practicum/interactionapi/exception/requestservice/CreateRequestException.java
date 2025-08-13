package ru.practicum.interactionapi.exception.requestservice;

/**
 * Исключение, выбрасываемое сервисом, если невозможно создать заявку на участие в событии.
 */
public class CreateRequestException extends RuntimeException {
    /**
     * Конструктор.
     *
     * @param message сообщение, содержащее подробности исключения.
     */
    public CreateRequestException(String message) {
        super(message);
    }
}
