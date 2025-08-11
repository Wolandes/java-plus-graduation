package ru.practicum.category.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.api.dto.category.CategoryDto;
import ru.practicum.api.dto.category.CreateCategoryDto;
import ru.practicum.category.model.Category;

import java.util.Collection;

@Component
public class CategoryMapper {
    public Category mapToCategory(CreateCategoryDto createCategoryDto) {
        return Category.builder()
                .name(checkOnNull(createCategoryDto.getName()))
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

    private String checkOnNull(String word) {
        if (word != null) {
            return word.trim();
        } else {
            return null;
        }
    }
}
