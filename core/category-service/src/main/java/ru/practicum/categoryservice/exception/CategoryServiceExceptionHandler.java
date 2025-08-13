package ru.practicum.categoryservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.api.exception.categoryservice.CategoryNotFoundException;
import ru.practicum.api.exception.categoryservice.CategoryWithSameNameAlreadyExistsException;
import ru.practicum.api.exception.categoryservice.DeleteCategoryException;

@RestControllerAdvice
public class CategoryServiceExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Exception> handleCategoryNotFoundException(final CategoryNotFoundException categoryNotFoundException) {
        return new ResponseEntity<>(categoryNotFoundException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Exception> handleCategoryWithSameNameAlreadyExistsException(final CategoryWithSameNameAlreadyExistsException categoryWithSameNameAlreadyExistsException) {
        return new ResponseEntity<>(categoryWithSameNameAlreadyExistsException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<Exception> handleDeleteCategoryException(final DeleteCategoryException deleteCategoryException) {
        return new ResponseEntity<>(deleteCategoryException, HttpStatus.CONFLICT);
    }
}
