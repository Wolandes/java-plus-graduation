package ru.practicum.interactionapi.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.interactionapi.dto.categoryservice.CategoryDto;

import java.util.Collection;

@FeignClient(name = "category-service")
public interface CategoryServiceClient {
    @GetMapping("/interaction/categories")
    Collection<CategoryDto> getCategories(@RequestParam(name = "ids") Collection<Long> categoriesIds);

    @GetMapping("/interaction/categories/{categoryId}")
    CategoryDto getCategory(@PathVariable Long categoryId);

    @GetMapping("/interaction/categories/check/existence/by/id/{categoryId}")
    boolean isCategoryExists(@PathVariable Long categoryId);
}
