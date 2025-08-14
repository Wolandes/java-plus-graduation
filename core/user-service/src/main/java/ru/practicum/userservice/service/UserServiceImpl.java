package ru.practicum.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.api.dto.userservice.CreateUserDto;
import ru.practicum.api.dto.userservice.UserDto;
import ru.practicum.api.exception.userservice.UserNotFoundException;
import ru.practicum.api.exception.userservice.UserWithSameEmailAlreadyExistsException;
import ru.practicum.api.pageable.PageOffset;
import ru.practicum.userservice.repository.UserRepository;
import ru.practicum.userservice.mapper.UserMapper;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDto createUser(CreateUserDto createUserDto) throws UserWithSameEmailAlreadyExistsException {
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
    public boolean isUserExists(Long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public UserDto getUser(Long userId) throws UserNotFoundException {
        return userMapper.mapToUserDto(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId)));
    }

    @Override
    public void deleteUser(Long userId) throws UserNotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        userRepository.deleteById(userId);
    }
}
