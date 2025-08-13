package ru.practicum.compilationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.interactionapi.exception.compilationservice.CompilationNotFoundException;

@RestControllerAdvice
public class CompilationServiceExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Exception> handleCompilationNotFoundException(final CompilationNotFoundException compilationNotFoundException) {
        return new ResponseEntity<>(compilationNotFoundException, HttpStatus.NOT_FOUND);
    }
}
