package ru.practicum.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.api.dto.event.EventDto;
import ru.practicum.api.dto.event.EventShortDto;

import java.util.Collection;

/**
 * Контракт клиента сервиса для работы с событиями.
 */
@FeignClient(value = "event-service")
public interface EventServiceClient {
    /**
     * Получить коллекцию событий.
     *
     * @param eventIds идентификаторы событий.
     * @return трансферный объект, содержащий данные о событии.
     */
    @GetMapping("/interaction/events")
    Collection<EventShortDto> getEvents(@RequestParam(name = "ids") Collection<Long> eventIds);

    /**
     * Получить событие.
     *
     * @param eventId идентификатор события.
     * @return трансферный объект, содержащий данные о событии.
     */
    @GetMapping("/interaction/events/{eventId}")
    EventDto getEvent(@PathVariable Long eventId);

    /**
     * Проверить существует ли событие.
     *
     * @param eventId идентификатор события.
     * @return признак существует ли событие.
     */
    @GetMapping("/interaction/events/check/existence/by/id/{eventId}")
    boolean isEventExists(@PathVariable Long eventId);

    /**
     * Проверить существуют ли события с данной категорией.
     *
     * @param categoryId идентификатор категории.
     * @return признак существуют ли события с данной категорией.
     */
    @GetMapping("/interaction/events/check/existence/with/category/{categoryId}")
    boolean isEventsWithCategoryExists(@PathVariable Long categoryId);

    /**
     * Проверить опубликовано ли событие.
     *
     * @param eventId идентификатор события.
     * @return признак, опубликовано ли событие.
     */
    @GetMapping("/interaction/events/check/publication/{eventId}")
    boolean isEventPublished(@PathVariable Long eventId);

    /**
     * Подтвердить участие в событии.
     *
     * @param eventId идентификатор события.
     */
    @PatchMapping("/interaction/events/{eventId}/participation/confirm")
    void confirmParticipation(@PathVariable Long eventId);

    /**
     * Отменить участие в событии.
     *
     * @param eventId идентификатор события.
     */
    @PatchMapping("/interaction/events/{eventId}/participation/reject")
    void rejectParticipation(@PathVariable Long eventId);
}
