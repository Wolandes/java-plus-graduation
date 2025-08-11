package ru.practicum.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.api.client.CategoryServiceClient;
import ru.practicum.category.service.CategoryService;
import ru.practicum.api.dto.category.CategoryDto;
import ru.practicum.api.exception.category.CategoryNotFoundException;

import java.util.Collection;

@RequestMapping("/interaction/categories")
@RequiredArgsConstructor
@RestController
@Slf4j
public class ApiController implements CategoryServiceClient {
    private final CategoryService categoryService;

    @GetMapping
    public Collection<CategoryDto> getCategories(@RequestParam(name = "ids") Collection<Long> categoriesIds) {
        log.info("Get categories with ids = {}", categoriesIds);
        return categoryService.getCategories(categoriesIds);
    }

    @GetMapping("/{categoryId}")
    public CategoryDto getCategory(@PathVariable Long categoryId) throws CategoryNotFoundException {
        log.info("Get category with id = {}", categoryId);
        return categoryService.getCategory(categoryId);
    }

    @GetMapping("/check/existence/by/id/{categoryId}")
    public boolean isCategoryExists(@PathVariable Long categoryId) {
        log.info("Check category with id = {} existence", categoryId);
        return categoryService.isCategoryExists(categoryId);
    }
}
