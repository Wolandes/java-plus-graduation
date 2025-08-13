package ru.practicum.interactionapi.dto.categoryservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Трансферный объект, содержащий данные о категории.
 */
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
public class CategoryDto {
    /**
     * Уникальный идентификатор категории.
     */
    private Long id;

    /**
     * Название категории.
     */
    private String name;
}
