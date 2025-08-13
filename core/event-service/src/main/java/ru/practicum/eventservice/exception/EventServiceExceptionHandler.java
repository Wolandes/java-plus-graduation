package ru.practicum.eventservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.interactionapi.exception.categoryservice.CategoryNotFoundException;
import ru.practicum.interactionapi.exception.eventservice.AccessToEventForbiddenException;
import ru.practicum.interactionapi.exception.eventservice.EventEditingException;
import ru.practicum.interactionapi.exception.eventservice.EventNotFoundException;
import ru.practicum.interactionapi.exception.eventservice.InvalidEventDateException;
import ru.practicum.interactionapi.exception.userservice.UserNotFoundException;

/**
 * Обработчик исключений, возникающих в сервисе.
 */
@RestControllerAdvice
public class EventServiceExceptionHandler {
    /**
     * Обработать исключение, выбрасываемое сервисом, если доступ к событию запрещен.
     *
     * @param accessToEventForbiddenException исключение, выбрасываемое сервисом, если доступ к событию запрещен.
     * @return результат обработки исключения.
     */
    @ExceptionHandler
    public ResponseEntity<Exception> handleAccessToEventForbiddenException(final AccessToEventForbiddenException accessToEventForbiddenException) {
        return new ResponseEntity<>(accessToEventForbiddenException, HttpStatus.FORBIDDEN);
    }

    /**
     * Обработать исключение, выбрасываемое сервисом, если категория не была найдена.
     *
     * @param categoryNotFoundException исключение, выбрасываемое сервисом, если категория не была найдена.
     * @return результат обработки исключения.
     */
    @ExceptionHandler
    public ResponseEntity<Exception> handleCategoryNotFoundException(final CategoryNotFoundException categoryNotFoundException) {
        return new ResponseEntity<>(categoryNotFoundException, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обработать исключение, выбрасываемое сервисом, если была произведена попытка редактирования события, которое запрещено редактировать.
     *
     * @param eventEditingException исключение, выбрасываемое сервисом, если была произведена попытка редактирования события, которое запрещено редактировать.
     * @return результат обработки исключения.
     */
    @ExceptionHandler
    public ResponseEntity<Exception> handleEventEditingException(final EventEditingException eventEditingException) {
        return new ResponseEntity<>(eventEditingException, HttpStatus.CONFLICT);
    }

    /**
     * Обработать исключение, выбрасываемое сервисом, если событие не было найдено.
     *
     * @param eventNotFoundException исключение, выбрасываемое сервисом, если событие не было найдено.
     * @return результат обработки исключения.
     */
    @ExceptionHandler
    public ResponseEntity<Exception> handleEventNotFoundException(final EventNotFoundException eventNotFoundException) {
        return new ResponseEntity<>(eventNotFoundException, HttpStatus.NOT_FOUND);
    }

    /**
     * Обработать исключение, выбрасываемое сервисом, если дата события некорректная.
     *
     * @param invalidEventDateException исключение, выбрасываемое сервисом, если дата события некорректная.
     * @return результат обработки исключения.
     */
    @ExceptionHandler
    public ResponseEntity<Exception> handleInvalidEventDateException(final InvalidEventDateException invalidEventDateException) {
        return new ResponseEntity<>(invalidEventDateException, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обработать исключение, выбрасываемое сервисом, если пользователь не был найден.
     *
     * @param userNotFoundException исключение, выбрасываемое сервисом, если пользователь не был найден.
     * @return результат обработки исключения.
     */
    @ExceptionHandler
    public ResponseEntity<Exception> handleUserNotFoundException(final UserNotFoundException userNotFoundException) {
        return new ResponseEntity<>(userNotFoundException, HttpStatus.BAD_REQUEST);
    }
}
