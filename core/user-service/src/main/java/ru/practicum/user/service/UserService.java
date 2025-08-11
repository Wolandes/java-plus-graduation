package ru.practicum.user.service;

import ru.practicum.api.dto.user.CreateUserDto;
import ru.practicum.api.dto.user.UserDto;

import java.util.Collection;

public interface UserService {
    UserDto createUser(CreateUserDto createUserDto);

    Collection<UserDto> getUsers(Collection<Long> userIds, int from, int size);

    Collection<UserDto> getUsers(Collection<Long> userIds);

    UserDto getUser(Long userId);

    void deleteUser(Long userId);

    boolean isUserExists(Long userId);
}
