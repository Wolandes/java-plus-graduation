package ru.practicum.categoryservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.categoryservice.model.Category;
import ru.practicum.categoryservice.repository.CategoryRepository;
import ru.practicum.categoryservice.mapper.CategoryMapper;
import ru.practicum.api.dto.categoryservice.CategoryDto;
import ru.practicum.api.dto.categoryservice.CreateCategoryDto;
import ru.practicum.api.dto.categoryservice.UpdateCategoryDto;
import ru.practicum.api.exception.categoryservice.CategoryNotFoundException;
import ru.practicum.api.exception.categoryservice.CategoryWithSameNameAlreadyExistsException;
import ru.practicum.api.exception.categoryservice.DeleteCategoryException;
import ru.practicum.api.client.EventServiceClient;
import ru.practicum.api.pageable.PageOffset;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final EventServiceClient eventServiceClient;

    @Override
    public CategoryDto createCategory(CreateCategoryDto createCategoryDto) throws CategoryWithSameNameAlreadyExistsException {
        if (categoryRepository.existsByName(createCategoryDto.getName())) {
            throw new CategoryWithSameNameAlreadyExistsException(createCategoryDto.getName());
        }

        return categoryMapper.mapToCategoryDto(categoryRepository.save(categoryMapper.mapToCategory(createCategoryDto)));
    }

    @Override
    public Collection<CategoryDto> getCategories(int from, int size) {
        return categoryMapper.mapToCategoryDtoCollection(categoryRepository.findAll(PageOffset.of(from, size, Sort.by("id").ascending())).getContent());
    }

    @Override
    public Collection<CategoryDto> getCategories(Collection<Long> categoriesIds) {
        return categoryMapper.mapToCategoryDtoCollection(categoryRepository.findAllById(categoriesIds));
    }

    @Override
    public CategoryDto getCategory(long categoryId) throws CategoryNotFoundException {
        return categoryMapper.mapToCategoryDto(findCategory(categoryId));
    }

    @Override
    public CategoryDto updateCategory(long categoryId, UpdateCategoryDto updateCategoryDto) throws CategoryNotFoundException, CategoryWithSameNameAlreadyExistsException {
        Category category = findCategory(categoryId);
        if (category.getName().equalsIgnoreCase(updateCategoryDto.getName())) {
            return categoryMapper.mapToCategoryDto(category);
        }

        if (categoryRepository.existsByName(updateCategoryDto.getName())) {
            throw new CategoryWithSameNameAlreadyExistsException(updateCategoryDto.getName());
        }

        category.setName(updateCategoryDto.getName());
        return categoryMapper.mapToCategoryDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(long categoryId) throws CategoryNotFoundException {
        if (eventServiceClient.isEventsWithCategoryExists(categoryId)) {
            throw new DeleteCategoryException("Невозможно удалить категорию с привязанными событиями");
        }

        categoryRepository.delete(findCategory(categoryId));
    }

    @Override
    public boolean isCategoryExists(long categoryId) {
        return categoryRepository.existsById(categoryId);
    }

    private Category findCategory(Long categoryId) throws CategoryNotFoundException {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }
}
