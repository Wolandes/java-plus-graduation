package ru.practicum.api.exception.user;


public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(long userId) {
        super(String.format("Пользователь не найден c id: " + userId));
    }
}
