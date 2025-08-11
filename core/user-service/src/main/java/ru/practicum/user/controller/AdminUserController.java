package ru.practicum.user.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.api.dto.user.CreateUserDto;
import ru.practicum.api.dto.user.UserDto;
import ru.practicum.user.service.UserService;

import java.util.Collection;

/**
 * Контроллер для работы с пользователями (API администратора).
 */
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@RestController
@Slf4j
public class AdminUserController {
    /**
     * Сервис для работы с пользователями.
     */
    private final UserService userService;

    /**
     * Добавить нового пользователя.
     *
     * @param createUserDto трансферный объект, содержащий данные для добавления нового пользователя.
     * @return новый пользователь.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        log.info("Create user - {}", createUserDto);
        return userService.createUser(createUserDto);
    }

    /**
     * Получить коллекцию пользователей.
     *
     * @param userIds коллекция идентификаторов пользователей, которых надо получить.
     * @param from    количество пользователей, которое необходимо пропустить.
     * @param size    количество пользователей, которое необходимо получить.
     * @return коллекция пользователей.
     */
    @GetMapping
    public Collection<UserDto> getUsers(@RequestParam(name = "ids", required = false) Collection<Long> userIds,
                                        @RequestParam(defaultValue = "0") @Min(0) int from,
                                        @RequestParam(defaultValue = "10") @Min(1) int size) {
        log.info("Get users - userIds: {}, from: {}, size: {}", userIds, from, size);
        return userService.getUsers(userIds, from, size);
    }

    /**
     * Удалить пользователя.
     *
     * @param userId идентификатор пользователя.
     */
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable @Positive Long userId) {
        log.info("Delete user with id = {}", userId);
        userService.deleteUser(userId);
    }
}
