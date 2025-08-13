package ru.practicum.interactionapi.exception.categoryservice;

/**
 * Исключение, выбрасываемое сервисом, если невозможно удалить категорию.
 */
public class DeleteCategoryException extends RuntimeException {
    /**
     * Конструктор.
     *
     * @param message сообщение, содержащее подробности исключения.
     */
    public DeleteCategoryException(String message) {
        super(message);
    }
}
