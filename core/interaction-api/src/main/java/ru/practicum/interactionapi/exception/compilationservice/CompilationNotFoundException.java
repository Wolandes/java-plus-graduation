package ru.practicum.interactionapi.exception.compilationservice;

/**
 * Исключение, выбрасываемое сервисом, если подборка событий не была найдена.
 */
public class CompilationNotFoundException extends RuntimeException {
    /**
     * Конструктор.
     *
     * @param compilationId идентификатор подборки событий.
     */
    public CompilationNotFoundException(long compilationId) {
        super(String.format("Подборка событий с id=%d не найдена", compilationId));
    }
}