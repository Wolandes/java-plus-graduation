package ru.practicum.user.controller;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.api.client.UserServiceClient;
import ru.practicum.api.dto.user.UserDto;
import ru.practicum.user.service.UserService;

import java.util.Collection;

@RequestMapping("/interaction/users")
@RequiredArgsConstructor
@RestController
@Slf4j
public class ApiController implements UserServiceClient {
    private final UserService userService;

    @GetMapping
    public Collection<UserDto> getUsers(@RequestParam(name = "ids") Collection<Long> userIds) {
        log.info("Get users with ids = {}", userIds);
        return userService.getUsers(userIds);
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable @Positive Long userId){
        log.info("Get user with id = {}", userId);
        return userService.getUser(userId);
    }

    @GetMapping("/check/existence/by/id/{userId}")
    public boolean isUserExists(@PathVariable Long userId) {
        log.info("Check user with id = {} existence", userId);
        return userService.isUserExists(userId);
    }
}
