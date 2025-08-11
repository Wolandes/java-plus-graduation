package ru.practicum.user.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.api.dto.user.UserDto;
import ru.practicum.user.model.User;
import ru.practicum.api.dto.user.CreateUserDto;

import java.util.Collection;

@Component
public class UserMapper {
    public User mapToUser(CreateUserDto createUserDto) {
        return User.builder()
                .name(checkOnNull(createUserDto.getName()))
                .email(checkOnNull(createUserDto.getEmail()))
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

    private String checkOnNull(String word) {
        if (word != null) {
            return word.trim();
        } else {
            return null;
        }
    }
}
