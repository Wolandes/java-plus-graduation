package ru.practicum.compilation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.api.exception.compilation.CompilationNotFoundException;

@RestControllerAdvice
public class CompilationExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Exception> handleCompilationNotFoundException(final CompilationNotFoundException compilationNotFoundException) {
        return new ResponseEntity<>(compilationNotFoundException, HttpStatus.NOT_FOUND);
    }
}
