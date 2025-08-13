package ru.practicum.interactionapi.exception.eventservice;

/**
 * Исключение, выбрасываемое сервисом, если событие не было найдено.
 */
public class EventNotFoundException extends RuntimeException {
    /**
     * Конструктор.
     *
     * @param eventId идентификатор события.
     */
    public EventNotFoundException(long eventId) {
        super(String.format("Событие с id=%d не найдено", eventId));
    }
}
