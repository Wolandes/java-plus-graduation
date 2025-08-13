package ru.practicum.eventservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.eventservice.model.Event;

/**
 * Контракт хранилища данных о событиях
 */
public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {
    /**
     * Проверить существуют ли события с данной категорией.
     *
     * @param categoryId идентификатор категории.
     * @return признак существуют ли события с данной категорией.
     */
    boolean existsByCategoryId(Long categoryId);
}
