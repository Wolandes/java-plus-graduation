package ru.practicum.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.api.dto.user.UserDto;

import java.util.Collection;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @GetMapping("/interaction/users")
    Collection<UserDto> getUsers(@RequestParam(name = "ids") Collection<Long> userIds);

    @GetMapping("/interaction/users/{userId}")
    UserDto getUser(@PathVariable Long userId);

    @GetMapping("/interaction/users/check/existence/by/id/{userId}")
    boolean isUserExists(@PathVariable Long userId);
}
