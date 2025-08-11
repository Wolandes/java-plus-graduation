package ru.practicum.api.exception.user;


public class UserWithSameEmailAlreadyExistsException extends RuntimeException {
    public UserWithSameEmailAlreadyExistsException(String email) {
        super(String.format("Адрес электронной почты " + email + " уже занят другим пользователем"));
    }
}
