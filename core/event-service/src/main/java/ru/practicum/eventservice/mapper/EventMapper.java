package ru.practicum.eventservice.mapper;

import ewm.EndpointStatDto;
import ewm.client.StatsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.eventservice.model.Event;
import ru.practicum.eventservice.model.Location;
import ru.practicum.api.dto.categoryservice.CategoryDto;
import ru.practicum.api.dto.eventservice.*;
import ru.practicum.api.dto.userservice.UserDto;
import ru.practicum.api.openfeign.CategoryServiceClient;
import ru.practicum.api.openfeign.UserServiceClient;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventMapper {
    private final CategoryServiceClient categoryServiceClient;

    private final StatsClient statsClient;

    private final UserServiceClient userServiceClient;

    public Event mapToEvent(CreateEventDto createEventDto, Long initiatorId, Long categoryId) {
        return Event.builder()
                .createdOn(LocalDateTime.now())
                .initiatorId(initiatorId)
                .title(createEventDto.getTitle())
                .annotation(createEventDto.getAnnotation())
                .description(createEventDto.getDescription())
                .eventDate(createEventDto.getEventDate())
                .categoryId(categoryId)
                .location(Location.builder().lat(createEventDto.getLocation().getLat()).lon(createEventDto.getLocation().getLon()).build())
                .paid(createEventDto.isPaid())
                .participantLimit(createEventDto.getParticipantLimit())
                .requestModeration(createEventDto.isRequestModeration())
                .state(EventState.PENDING)
                .build();
    }

    public EventDto mapToEventDto(Event event, UserDto initiator, CategoryDto category, Long views) {
        return EventDto.builder()
                .id(event.getId())
                .createdOn(event.getCreatedOn())
                .initiator(initiator)
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .category(category)
                .location(LocationDto.builder().lat(event.getLocation().getLat()).lon(event.getLocation().getLon()).build())
                .publishedOn(event.getPublishedOn())
                .paid(event.isPaid())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.isRequestModeration())
                .confirmedRequests(event.getConfirmedRequests())
                .state(event.getState())
                .views(views)
                .build();
    }

    public EventShortDto mapToEventShortDto(Event event, UserDto initiator, CategoryDto categoryDto, Long views) {
        return EventShortDto.builder()
                .id(event.getId())
                .initiator(initiator)
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .eventDate(event.getEventDate())
                .category(categoryDto)
                .paid(event.isPaid())
                .confirmedRequests(event.getConfirmedRequests())
                .views(views)
                .build();
    }

    public Collection<EventDto> mapToEventDtoCollection(Collection<Event> events) {
        Collection<Long> initiatorsIds = events.stream().map(Event::getInitiatorId).toList();
        Collection<Long> categoriesIds = events.stream().map(Event::getCategoryId).toList();

        Map<Long, UserDto> initiators = userServiceClient.getUsers(initiatorsIds).stream().collect(Collectors.toMap(UserDto::getId, userShortDto -> userShortDto));
        Map<Long, CategoryDto> categories = categoryServiceClient.getCategories(categoriesIds).stream().collect(Collectors.toMap(CategoryDto::getId, category -> category));
        Map<Long, Long> eventViews = getEventStats(events.stream().map(Event::getId).toList());

        return events.stream().map(event -> mapToEventDto(event, initiators.get(event.getInitiatorId()), categories.get(event.getCategoryId()), eventViews.getOrDefault(event.getId(), 0L))).toList();
    }

    public Collection<EventDto> mapToEventDtoCollection(Collection<Event> events, UserDto initiator) {
        Collection<Long> categoriesIds = events.stream().map(Event::getCategoryId).toList();

        Map<Long, CategoryDto> categories = categoryServiceClient.getCategories(categoriesIds).stream().collect(Collectors.toMap(CategoryDto::getId, category -> category));
        Map<Long, Long> eventViews = getEventStats(events.stream().map(Event::getId).toList());

        return events.stream().map(event -> mapToEventDto(event, initiator, categories.get(event.getCategoryId()), eventViews.getOrDefault(event.getId(), 0L))).toList();
    }

    public Collection<EventShortDto> mapToEventShortDtoCollection(Collection<Event> events) {
        Collection<Long> initiatorsIds = events.stream().map(Event::getInitiatorId).toList();
        Collection<Long> categoriesIds = events.stream().map(Event::getCategoryId).toList();

        Map<Long, UserDto> initiators = userServiceClient.getUsers(initiatorsIds).stream().collect(Collectors.toMap(UserDto::getId, userShortDto -> userShortDto));
        Map<Long, CategoryDto> categories = categoryServiceClient.getCategories(categoriesIds).stream().collect(Collectors.toMap(CategoryDto::getId, category -> category));
        Map<Long, Long> eventViews = getEventStats(events.stream().map(Event::getId).toList());

        return events.stream().map(event -> mapToEventShortDto(event, initiators.get(event.getInitiatorId()), categories.get(event.getCategoryId()), eventViews.getOrDefault(event.getId(), 0L))).toList();
    }

    public Collection<EventShortDto> mapToEventShortDtoCollection(Collection<Event> events, UserDto initiator) {
        Collection<Long> categoriesIds = events.stream().map(Event::getCategoryId).toList();

        Map<Long, CategoryDto> categories = categoryServiceClient.getCategories(categoriesIds).stream().collect(Collectors.toMap(CategoryDto::getId, category -> category));
        Map<Long, Long> eventViews = getEventStats(events.stream().map(Event::getId).toList());

        return events.stream().map(event -> mapToEventShortDto(event, initiator, categories.get(event.getCategoryId()), eventViews.getOrDefault(event.getId(), 0L))).toList();
    }

    private Map<Long, Long> getEventStats(Collection<Long> eventIds) {
        Map<String, Long> urisMap = eventIds.stream().collect(Collectors.toMap((eventId) -> "/events/" + eventId, (eventId) -> eventId));

        LocalDateTime start = LocalDateTime.of(2020, 5, 5, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2035, 5, 5, 0, 0, 0);

        try {
            return Objects.requireNonNull(statsClient.getStats(start, end, urisMap.keySet().stream().toList(), true).getBody())
                    .stream()
                    .collect(Collectors.toMap(dto -> urisMap.get(dto.getUri()), EndpointStatDto::getHits));

        } catch (Throwable ex) {
            return urisMap.values().stream().collect(Collectors.toMap((eventId) -> eventId, eventId -> 0L));
        }
    }
}
