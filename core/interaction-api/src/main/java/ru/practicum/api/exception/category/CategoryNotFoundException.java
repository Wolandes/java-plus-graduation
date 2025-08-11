package ru.practicum.api.exception.category;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(long categoryId) {
        super(String.format("Категория не найдена с id: " + categoryId));
    }
}
