package ru.practicum.category.controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.service.CategoryService;
import ru.practicum.api.dto.category.CategoryDto;
import ru.practicum.api.exception.category.CategoryNotFoundException;

import java.util.Collection;

@RequestMapping("/categories")
@RequiredArgsConstructor
@RestController
@Slf4j
public class PublicCategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<CategoryDto> getCategories(@RequestParam(value = "from", defaultValue = "0") @PositiveOrZero int from,
                                                 @RequestParam(value = "size", defaultValue = "10") @Positive int size) {
        log.info("Get {} categories starts with {}", size, from);
        return categoryService.getCategories(from, size);
    }

    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getCategory(@PathVariable Long categoryId) throws CategoryNotFoundException {
        log.info("Find category with id={}", categoryId);
        return categoryService.getCategory(categoryId);
    }
}
