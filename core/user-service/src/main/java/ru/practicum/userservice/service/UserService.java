package ru.practicum.userservice.service;

import ru.practicum.api.dto.userservice.CreateUserDto;
import ru.practicum.api.dto.userservice.UserDto;
import ru.practicum.api.exception.userservice.UserNotFoundException;
import ru.practicum.api.exception.userservice.UserWithSameEmailAlreadyExistsException;

import java.util.Collection;

public interface UserService {
    UserDto createUser(CreateUserDto createUserDto) throws UserWithSameEmailAlreadyExistsException;

    Collection<UserDto> getUsers(Collection<Long> userIds, int from, int size);

    Collection<UserDto> getUsers(Collection<Long> userIds);

    UserDto getUser(Long userId) throws UserNotFoundException;

    void deleteUser(Long userId) throws UserNotFoundException;

    boolean isUserExists(Long userId);
}
