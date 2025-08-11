package ru.practicum.api.exception.compilation;


public class CompilationNotFoundException extends RuntimeException {
    public CompilationNotFoundException(long compilationId) {
        super(String.format("Подборка событий не найдена с id: " + compilationId));
    }
}