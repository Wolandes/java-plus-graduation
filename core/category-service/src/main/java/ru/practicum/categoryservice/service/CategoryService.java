package ru.practicum.categoryservice.service;

import ru.practicum.interactionapi.dto.categoryservice.CategoryDto;
import ru.practicum.interactionapi.dto.categoryservice.CreateCategoryDto;
import ru.practicum.interactionapi.dto.categoryservice.UpdateCategoryDto;
import ru.practicum.interactionapi.exception.categoryservice.CategoryNotFoundException;
import ru.practicum.interactionapi.exception.categoryservice.CategoryWithSameNameAlreadyExistsException;

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
