package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.api.client.EventServiceClient;
import ru.practicum.api.dto.category.CategoryDto;
import ru.practicum.api.dto.category.CreateCategoryDto;
import ru.practicum.api.dto.category.UpdateCategoryDto;
import ru.practicum.api.exception.category.CategoryNotFoundException;
import ru.practicum.api.exception.category.CategoryWithSameNameAlreadyExistsException;
import ru.practicum.api.exception.category.DeleteCategoryException;
import ru.practicum.api.exception.user.UserNotFoundException;
import ru.practicum.api.pageable.PageOffset;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final EventServiceClient eventServiceClient;

    @Override
    public CategoryDto createCategory(CreateCategoryDto createCategoryDto) {
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
    public CategoryDto getCategory(long categoryId) {
        return categoryMapper.mapToCategoryDto(findCategory(categoryId));
    }

    @Override
    public CategoryDto updateCategory(long categoryId, UpdateCategoryDto updateCategoryDto){
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

    private Category findCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }
}
