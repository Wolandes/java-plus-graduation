package ru.practicum.userservice.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.api.dto.userservice.CreateUserDto;
import ru.practicum.api.dto.userservice.UserDto;
import ru.practicum.userservice.model.User;

import java.util.Collection;

@Component
public class UserMapper {
    public User mapToUser(CreateUserDto createUserDto) {
        return User.builder()
                .name(createUserDto.getName() != null ? createUserDto.getName().trim() : null)
                .email(createUserDto.getEmail() != null ? createUserDto.getEmail().trim() : null)
                .build();
    }

    public UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public Collection<UserDto> mapToUserDtoCollection(Collection<User> users) {
        return users.stream().map(this::mapToUserDto).toList();
    }
}
