package ru.practicum.categoryservice.service;

import ru.practicum.api.dto.categoryservice.CategoryDto;
import ru.practicum.api.dto.categoryservice.CreateCategoryDto;
import ru.practicum.api.dto.categoryservice.UpdateCategoryDto;
import ru.practicum.api.exception.categoryservice.CategoryNotFoundException;
import ru.practicum.api.exception.categoryservice.CategoryWithSameNameAlreadyExistsException;

import java.util.Collection;

public interface CategoryService {
    CategoryDto createCategory(CreateCategoryDto createCategoryDto) throws CategoryWithSameNameAlreadyExistsException;

    Collection<CategoryDto> getCategories(int from, int size);

    Collection<CategoryDto> getCategories(Collection<Long> categoriesIds);

    CategoryDto getCategory(long categoryId) throws CategoryNotFoundException;

    CategoryDto updateCategory(long categoryId, UpdateCategoryDto updateCategoryDto) throws CategoryNotFoundException, CategoryWithSameNameAlreadyExistsException;

    void deleteCategory(long categoryId) throws CategoryNotFoundException;

    boolean isCategoryExists(long categoryId);
}
