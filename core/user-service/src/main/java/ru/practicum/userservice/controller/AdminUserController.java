package ru.practicum.userservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.interactionapi.dto.userservice.CreateUserDto;
import ru.practicum.interactionapi.dto.userservice.UserDto;
import ru.practicum.interactionapi.exception.userservice.UserNotFoundException;
import ru.practicum.interactionapi.exception.userservice.UserWithSameEmailAlreadyExistsException;
import ru.practicum.userservice.service.UserService;

import java.util.Collection;

@RequestMapping("/admin/users")
@RequiredArgsConstructor
@RestController
@Slf4j
public class AdminUserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Valid CreateUserDto createUserDto) throws UserWithSameEmailAlreadyExistsException {
        log.info("Create user - {}", createUserDto);
        return userService.createUser(createUserDto);
    }

    @GetMapping
    public Collection<UserDto> getUsers(@RequestParam(name = "ids", required = false) Collection<Long> userIds,
                                        @RequestParam(defaultValue = "0") @Min(0) int from,
                                        @RequestParam(defaultValue = "10") @Min(1) int size) {
        log.info("Get users - userIds: {}, from: {}, size: {}", userIds, from, size);
        return userService.getUsers(userIds, from, size);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable @Positive Long userId) throws UserNotFoundException {
        log.info("Delete user with id = {}", userId);
        userService.deleteUser(userId);
    }
}
