package ru.practicum.categoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.categoryservice.model.Category;

/**
 * Контракт хранилища данных о категориях.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String categoryName);
}
