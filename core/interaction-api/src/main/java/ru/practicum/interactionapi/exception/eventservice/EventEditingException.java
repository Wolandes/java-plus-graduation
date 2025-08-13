package ru.practicum.interactionapi.exception.eventservice;

/**
 * Исключение, выбрасываемое сервисом, если была произведена попытка редактирования события, которое запрещено редактировать.
 */
public class EventEditingException extends RuntimeException {
    /**
     * Конструктор.
     *
     * @param eventId идентификатор события.
     */
    public EventEditingException(long eventId) {
        super(String.format("Событие с id=%d запрещено для редактирования", eventId));
    }

    /**
     * Конструктор.
     *
     * @param message сообщение, содержащее подробности исключения.
     */
    public EventEditingException(String message) {
        super(message);
    }
}