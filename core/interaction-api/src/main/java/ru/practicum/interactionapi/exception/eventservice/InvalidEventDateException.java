package ru.practicum.interactionapi.exception.eventservice;

/**
 * Исключение, выбрасываемое сервисом, если дата события некорректная.
 */
public class InvalidEventDateException extends RuntimeException {
    /**
     * Конструктор.
     *
     * @param message сообщение, содержащее подробности исключения.
     */
    public InvalidEventDateException(String message) {
        super(message);
    }
}
