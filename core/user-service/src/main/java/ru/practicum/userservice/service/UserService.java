package ru.practicum.userservice.service;

import ru.practicum.interactionapi.dto.userservice.CreateUserDto;
import ru.practicum.interactionapi.dto.userservice.UserDto;
import ru.practicum.interactionapi.exception.userservice.UserNotFoundException;
import ru.practicum.interactionapi.exception.userservice.UserWithSameEmailAlreadyExistsException;

import java.util.Collection;

public interface UserService {
    UserDto createUser(CreateUserDto createUserDto) throws UserWithSameEmailAlreadyExistsException;

    Collection<UserDto> getUsers(Collection<Long> userIds, int from, int size);

    Collection<UserDto> getUsers(Collection<Long> userIds);

    UserDto getUser(Long userId) throws UserNotFoundException;

    void deleteUser(Long userId) throws UserNotFoundException;

    boolean isUserExists(Long userId);
}
