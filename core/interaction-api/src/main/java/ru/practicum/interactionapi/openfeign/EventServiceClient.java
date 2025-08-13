package ru.practicum.interactionapi.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.interactionapi.dto.eventservice.EventDto;
import ru.practicum.interactionapi.dto.eventservice.EventShortDto;

import java.util.Collection;

@FeignClient(value = "event-service")
public interface EventServiceClient {
    @GetMapping("/interaction/events")
    Collection<EventShortDto> getEvents(@RequestParam(name = "ids") Collection<Long> eventIds);

    @GetMapping("/interaction/events/{eventId}")
    EventDto getEvent(@PathVariable Long eventId);

    @GetMapping("/interaction/events/check/existence/by/id/{eventId}")
    boolean isEventExists(@PathVariable Long eventId);

    @GetMapping("/interaction/events/check/existence/with/category/{categoryId}")
    boolean isEventsWithCategoryExists(@PathVariable Long categoryId);

    @GetMapping("/interaction/events/check/publication/{eventId}")
    boolean isEventPublished(@PathVariable Long eventId);

    @PatchMapping("/interaction/events/{eventId}/participation/confirm")
    void confirmParticipation(@PathVariable Long eventId);

    @PatchMapping("/interaction/events/{eventId}/participation/reject")
    void rejectParticipation(@PathVariable Long eventId);
}
