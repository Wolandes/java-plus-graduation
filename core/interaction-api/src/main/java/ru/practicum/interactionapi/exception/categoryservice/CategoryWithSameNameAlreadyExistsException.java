package ru.practicum.interactionapi.exception.categoryservice;

/**
 * Исключение, выбрасываемое сервисом, если категория с таким названием уже существует в БД.
 */
public class CategoryWithSameNameAlreadyExistsException extends RuntimeException {
    /**
     * Конструктор.
     *
     * @param categoryName название категории.
     */
    public CategoryWithSameNameAlreadyExistsException(String categoryName) {
        super(String.format("Категория с названием %s уже существует", categoryName));
    }
}
