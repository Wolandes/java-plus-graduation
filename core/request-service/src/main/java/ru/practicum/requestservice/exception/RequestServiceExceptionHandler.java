package ru.practicum.requestservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.api.exception.eventservice.AccessToEventForbiddenException;
import ru.practicum.api.exception.eventservice.EventNotFoundException;
import ru.practicum.api.exception.requestservice.CreateRequestException;
import ru.practicum.api.exception.requestservice.UpdateRequestStatusException;
import ru.practicum.api.exception.userservice.UserNotFoundException;

@RestControllerAdvice
public class RequestServiceExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Exception> handleAccessToEventForbiddenException(final AccessToEventForbiddenException accessToEventForbiddenException) {
        return new ResponseEntity<>(accessToEventForbiddenException, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<Exception> handleCreateRequestException(final CreateRequestException createRequestException) {
        return new ResponseEntity<>(createRequestException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<Exception> handleEventNotFoundException(final EventNotFoundException eventNotFoundException) {
        return new ResponseEntity<>(eventNotFoundException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Exception> handleMissingPathVariableException(final MissingPathVariableException missingPathVariableException) {
        return new ResponseEntity<>(missingPathVariableException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Exception> handleUpdateRequestStatusException(final UpdateRequestStatusException updateRequestStatusException) {
        return new ResponseEntity<>(updateRequestStatusException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<Exception> handleUserNotFoundException(final UserNotFoundException userNotFoundException) {
        return new ResponseEntity<>(userNotFoundException, HttpStatus.BAD_REQUEST);
    }
}
