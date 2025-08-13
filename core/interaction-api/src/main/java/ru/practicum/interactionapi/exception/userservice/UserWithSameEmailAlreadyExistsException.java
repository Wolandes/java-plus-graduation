package ru.practicum.interactionapi.exception.userservice;

/**
 * Исключение, выбрасываемое сервисом, если производится попытка создания нового пользователя с адресом электронной почты, который уже занят другим пользователем.
 */
public class UserWithSameEmailAlreadyExistsException extends RuntimeException {
    /**
     * Конструктор.
     *
     * @param email адрес электронной почты пользователя.
     */
    public UserWithSameEmailAlreadyExistsException(String email) {
        super(String.format("Адрес электронной почты %s уже занят другим пользователем", email));
    }
}
