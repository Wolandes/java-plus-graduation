package ru.practicum.category.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.service.CategoryService;
import ru.practicum.api.dto.category.CategoryDto;
import ru.practicum.api.dto.category.CreateCategoryDto;
import ru.practicum.api.dto.category.UpdateCategoryDto;
import ru.practicum.api.exception.category.CategoryNotFoundException;
import ru.practicum.api.exception.category.CategoryWithSameNameAlreadyExistsException;

/**
 * Контроллер для работы с категориями (API администратора).
 */
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@RestController
@Slf4j
public class AdminCategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody @Valid CreateCategoryDto createCategoryDto) {
        log.info("Create category - {}", createCategoryDto);
        return categoryService.createCategory(createCategoryDto);
    }

    @PatchMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(@PathVariable @Positive Long categoryId,
                                      @RequestBody @Valid UpdateCategoryDto updateCategoryDto) {
        log.info("Update category with id={} - {}", categoryId, updateCategoryDto);
        return categoryService.updateCategory(categoryId, updateCategoryDto);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable @Positive Long categoryId) {
        log.info("Delete category with id={}", categoryId);
        categoryService.deleteCategory(categoryId);
    }
}
