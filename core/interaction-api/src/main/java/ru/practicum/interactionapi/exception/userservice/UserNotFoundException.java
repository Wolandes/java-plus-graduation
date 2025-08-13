package ru.practicum.interactionapi.exception.userservice;

/**
 * Исключение, выбрасываемое сервисом, если пользователь не был найден.
 */
public class UserNotFoundException extends RuntimeException {
    /**
     * Конструктор.
     *
     * @param userId идентификатор пользователя.
     */
    public UserNotFoundException(long userId) {
        super(String.format("Пользователь с id=%d не найден", userId));
    }
}
