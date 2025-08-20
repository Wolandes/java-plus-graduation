package ru.practicum.eventservice.mapper;

import ewm.client.AnalyzerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.eventservice.model.Event;
import ru.practicum.eventservice.model.Location;
import ru.practicum.api.dto.categoryservice.CategoryDto;
import ru.practicum.api.dto.eventservice.*;
import ru.practicum.api.dto.userservice.UserDto;
import ru.practicum.api.client.CategoryServiceClient;
import ru.practicum.api.client.UserServiceClient;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventMapper {
    private final CategoryServiceClient categoryServiceClient;
    private final UserServiceClient userServiceClient;
    private final AnalyzerClient analyzerClient;

    public Event mapToEvent(CreateEventDto createEventDto, Long initiatorId, Long categoryId) {
        return Event.builder()
                .createdOn(LocalDateTime.now())
                .initiatorId(initiatorId)
                .title(createEventDto.getTitle())
                .annotation(createEventDto.getAnnotation())
                .description(createEventDto.getDescription())
                .eventDate(createEventDto.getEventDate())
                .categoryId(categoryId)
                .location(Location.builder()
                        .lat(createEventDto.getLocation().getLat())
                        .lon(createEventDto.getLocation().getLon())
                        .build())
                .paid(createEventDto.isPaid())
                .participantLimit(createEventDto.getParticipantLimit())
                .requestModeration(createEventDto.isRequestModeration())
                .state(EventState.PENDING)
                .build();
    }

    public EventDto mapToEventDto(Event event, UserDto initiator, CategoryDto category) {
        return EventDto.builder()
                .id(event.getId())
                .createdOn(event.getCreatedOn())
                .initiator(initiator)
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .category(category)
                .location(LocationDto.builder()
                        .lat(event.getLocation().getLat())
                        .lon(event.getLocation().getLon())
                        .build())
                .publishedOn(event.getPublishedOn())
                .paid(event.isPaid())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.isRequestModeration())
                .confirmedRequests(event.getConfirmedRequests())
                .state(event.getState())
                .build();
    }

    public EventShortDto mapToEventShortDto(Event event, UserDto initiator, CategoryDto categoryDto) {
        return EventShortDto.builder()
                .id(event.getId())
                .initiator(initiator)
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .eventDate(event.getEventDate())
                .category(categoryDto)
                .paid(event.isPaid())
                .confirmedRequests(event.getConfirmedRequests())
                .build();
    }

    public Collection<EventDto> mapToEventDtoCollection(Collection<Event> events) {
        Collection<Long> initiatorsIds = events.stream().map(Event::getInitiatorId).toList();
        Collection<Long> categoriesIds = events.stream().map(Event::getCategoryId).toList();

        Map<Long, UserDto> initiators = userServiceClient.getUsers(initiatorsIds)
                .stream().collect(Collectors.toMap(UserDto::getId, user -> user));
        Map<Long, CategoryDto> categories = categoryServiceClient.getCategories(categoriesIds)
                .stream().collect(Collectors.toMap(CategoryDto::getId, category -> category));

        //получим рейтинг мероприятия из сервиса
        Map<Long, Double> ratingsMap = analyzerClient.getInteractionsCount(events.stream().map(Event::getId).toList());

        return events.stream()
                .map(event -> mapToEventDto(event, initiators.get(event.getInitiatorId()), categories.get(event.getCategoryId())))
                .peek(event -> event.setRating(ratingsMap.getOrDefault(event.getId(), 0.0)))
                .toList();
    }

    public Collection<EventDto> mapToEventDtoCollection(Collection<Event> events, UserDto initiator) {
        Collection<Long> categoriesIds = events.stream().map(Event::getCategoryId).toList();

        Map<Long, CategoryDto> categories = categoryServiceClient.getCategories(categoriesIds)
                .stream().collect(Collectors.toMap(CategoryDto::getId, category -> category));

        return events.stream()
                .map(event -> mapToEventDto(event, initiator, categories.get(event.getCategoryId())))
                .toList();
    }

    public Collection<EventShortDto> mapToEventShortDtoCollection(Collection<Event> events) {
        Collection<Long> initiatorsIds = events.stream().map(Event::getInitiatorId).toList();
        Collection<Long> categoriesIds = events.stream().map(Event::getCategoryId).toList();

        Map<Long, UserDto> initiators = userServiceClient.getUsers(initiatorsIds)
                .stream().collect(Collectors.toMap(UserDto::getId, user -> user));
        Map<Long, CategoryDto> categories = categoryServiceClient.getCategories(categoriesIds)
                .stream().collect(Collectors.toMap(CategoryDto::getId, category -> category));

        //получим рейтинг мероприятия из сервиса
        Map<Long, Double> ratingsMap = analyzerClient.getInteractionsCount(events.stream().map(Event::getId).toList());

        return events.stream()
                .map(event -> mapToEventShortDto(event, initiators.get(event.getInitiatorId()), categories.get(event.getCategoryId())))
                .peek(event -> event.setRating(ratingsMap.getOrDefault(event.getId(), 0.0)))
                .toList();
    }

    public Collection<EventShortDto> mapToEventShortDtoCollection(Collection<Event> events, UserDto initiator) {
        Collection<Long> categoriesIds = events.stream().map(Event::getCategoryId).toList();

        Map<Long, CategoryDto> categories = categoryServiceClient.getCategories(categoriesIds)
                .stream().collect(Collectors.toMap(CategoryDto::getId, category -> category));

        return events.stream()
                .map(event -> mapToEventShortDto(event, initiator, categories.get(event.getCategoryId())))
                .toList();
    }
}
