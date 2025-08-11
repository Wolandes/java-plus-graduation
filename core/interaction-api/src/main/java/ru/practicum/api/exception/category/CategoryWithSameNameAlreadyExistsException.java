package ru.practicum.api.exception.category;

public class CategoryWithSameNameAlreadyExistsException extends RuntimeException {
    public CategoryWithSameNameAlreadyExistsException(String categoryName) {
        super(String.format("Категория уже существует c названием" + categoryName));
    }
}
