package ru.practicum.category.service;

import ru.practicum.api.dto.category.CategoryDto;
import ru.practicum.api.dto.category.CreateCategoryDto;
import ru.practicum.api.dto.category.UpdateCategoryDto;


import java.util.Collection;

public interface CategoryService {
    CategoryDto createCategory(CreateCategoryDto createCategoryDto);

    Collection<CategoryDto> getCategories(int from, int size);

    Collection<CategoryDto> getCategories(Collection<Long> categoriesIds);

    CategoryDto getCategory(long categoryId);

    CategoryDto updateCategory(long categoryId, UpdateCategoryDto updateCategoryDto);

    void deleteCategory(long categoryId);

    boolean isCategoryExists(long categoryId);
}
