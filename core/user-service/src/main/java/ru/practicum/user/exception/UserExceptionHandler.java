package ru.practicum.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.api.exception.user.UserNotFoundException;
import ru.practicum.api.exception.user.UserWithSameEmailAlreadyExistsException;


@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Exception> handleUserNotFoundException(final UserNotFoundException userNotFoundException) {
        return new ResponseEntity<>(userNotFoundException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Exception> handleUserWithSameEmailAlreadyExistsException(final UserWithSameEmailAlreadyExistsException userWithSameEmailAlreadyExistsException) {
        return new ResponseEntity<>(userWithSameEmailAlreadyExistsException, HttpStatus.CONFLICT);
    }
}
