package ru.practicum.interactionapi.exception.categoryservice;

/**
 * Исключение, выбрасываемое сервисом, если категория не была найдена.
 */
public class CategoryNotFoundException extends RuntimeException {
    /**
     * Конструктор.
     *
     * @param categoryId идентификатор категории.
     */
    public CategoryNotFoundException(long categoryId) {
        super(String.format("Категория с id=%d не найдена", categoryId));
    }
}
