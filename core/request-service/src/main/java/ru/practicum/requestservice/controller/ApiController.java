package ru.practicum.requestservice.controller;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.api.dto.requestservice.RequestDto;
import ru.practicum.api.dto.requestservice.RequestStatus;
import ru.practicum.api.exception.requestservice.CreateRequestException;
import ru.practicum.requestservice.service.RequestService;

import java.util.List;

@RequestMapping("/interaction/request")
@RequiredArgsConstructor
@RestController
@Slf4j
public class ApiController {
    private final RequestService requestService;

    @GetMapping("/find-by-requester-and-event")
    public RequestDto findByRequesterIdAndEventId(@RequestParam Long requesterId, @RequestParam Long eventId) throws CreateRequestException {
        return requestService.findByRequesterIdAndEventId(requesterId, eventId);
    }

    @GetMapping("/find-by-events")
    List<RequestDto> findByEventIdInAndStatus(@RequestParam List<Long> eventIds, @RequestParam RequestStatus requestStatus) throws FeignException{
        return requestService.findByEventIdInAndStatus(eventIds, requestStatus);
    }
}
