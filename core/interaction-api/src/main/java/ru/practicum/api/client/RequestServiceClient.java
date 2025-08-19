package ru.practicum.api.client;

import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.api.dto.requestservice.RequestDto;
import ru.practicum.api.dto.requestservice.RequestStatus;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "request-service")
public interface RequestServiceClient {
    @GetMapping("/interaction/request/find-by-requester-and-event")
    Optional<RequestDto> findByRequesterIdAndEventId(@RequestParam Long requesterId, @RequestParam Long eventId) throws FeignException;

    @GetMapping("/interaction/request/find-by-events")
    List<RequestDto> findByEventIdInAndStatus(@RequestParam List<Long> eventIds, @RequestParam RequestStatus requestStatus) throws FeignException;
}
