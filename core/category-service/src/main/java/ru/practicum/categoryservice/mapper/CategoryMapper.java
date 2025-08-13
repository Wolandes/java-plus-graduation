package ru.practicum.categoryservice.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.categoryservice.model.Category;
import ru.practicum.api.dto.categoryservice.CategoryDto;
import ru.practicum.api.dto.categoryservice.CreateCategoryDto;

import java.util.Collection;

@Component
public class CategoryMapper {
    public Category mapToCategory(CreateCategoryDto createCategoryDto) {
        return Category.builder()
                .name(createCategoryDto.getName() != null ? createCategoryDto.getName().trim() : null)
                .build();
    }

    public CategoryDto mapToCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public Collection<CategoryDto> mapToCategoryDtoCollection(Collection<Category> categories) {
        return categories.stream().map(this::mapToCategoryDto).toList();
    }
}
