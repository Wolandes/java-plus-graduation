package ru.practicum.api.exception.category;

public class DeleteCategoryException extends RuntimeException {
    public DeleteCategoryException(String message) {
        super(message);
    }
}
