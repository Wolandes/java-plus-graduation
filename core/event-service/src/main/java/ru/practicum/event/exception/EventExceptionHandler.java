package ru.practicum.event.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.api.exception.category.CategoryNotFoundException;
import ru.practicum.api.exception.event.AccessToEventForbiddenException;
import ru.practicum.api.exception.event.EventEditingException;
import ru.practicum.api.exception.event.EventNotFoundException;
import ru.practicum.api.exception.event.InvalidEventDateException;
import ru.practicum.api.exception.user.UserNotFoundException;

@RestControllerAdvice
public class EventExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Exception> handleAccessToEventForbiddenException(final AccessToEventForbiddenException accessToEventForbiddenException) {
        return new ResponseEntity<>(accessToEventForbiddenException, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<Exception> handleCategoryNotFoundException(final CategoryNotFoundException categoryNotFoundException) {
        return new ResponseEntity<>(categoryNotFoundException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Exception> handleEventEditingException(final EventEditingException eventEditingException) {
        return new ResponseEntity<>(eventEditingException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<Exception> handleEventNotFoundException(final EventNotFoundException eventNotFoundException) {
        return new ResponseEntity<>(eventNotFoundException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Exception> handleInvalidEventDateException(final InvalidEventDateException invalidEventDateException) {
        return new ResponseEntity<>(invalidEventDateException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Exception> handleUserNotFoundException(final UserNotFoundException userNotFoundException) {
        return new ResponseEntity<>(userNotFoundException, HttpStatus.BAD_REQUEST);
    }
}
