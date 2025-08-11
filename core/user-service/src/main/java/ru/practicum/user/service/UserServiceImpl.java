package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.api.dto.user.CreateUserDto;
import ru.practicum.api.dto.user.UserDto;
import ru.practicum.api.exception.user.UserNotFoundException;
import ru.practicum.api.exception.user.UserWithSameEmailAlreadyExistsException;
import ru.practicum.api.pageable.PageOffset;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDto createUser(CreateUserDto createUserDto) {
        if (userRepository.existsByEmail(createUserDto.getEmail())) {
            throw new UserWithSameEmailAlreadyExistsException(createUserDto.getEmail());
        }
        return userMapper.mapToUserDto(userRepository.save(userMapper.mapToUser(createUserDto)));
    }

    @Override
    public Collection<UserDto> getUsers(Collection<Long> userIds, int from, int size) {
        if (userIds != null && !userIds.isEmpty()) {
            return userMapper.mapToUserDtoCollection(userRepository.findAllById(userIds));
        }

        return userMapper.mapToUserDtoCollection(userRepository.findAll(PageOffset.of(from, size)).getContent());
    }

    @Override
    public Collection<UserDto> getUsers(Collection<Long> userIds) {
        return userMapper.mapToUserDtoCollection(userRepository.findAllById(userIds));
    }

    @Override
    public UserDto getUser(Long userId) {
        return userMapper.mapToUserDto(findUser(userId));
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        userRepository.deleteById(userId);
    }

    public boolean isUserExists(Long userId) {
        return userRepository.existsById(userId);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }
}
