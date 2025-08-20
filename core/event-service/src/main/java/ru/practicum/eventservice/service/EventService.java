package ru.practicum.eventservice.service;

import ru.practicum.api.exception.eventservice.*;
import ru.practicum.eventservice.model.EventSearch;
import ru.practicum.api.dto.eventservice.CreateEventDto;
import ru.practicum.api.dto.eventservice.EventDto;
import ru.practicum.api.dto.eventservice.EventShortDto;
import ru.practicum.api.dto.eventservice.UpdateEventDto;
import ru.practicum.api.exception.categoryservice.CategoryNotFoundException;
import ru.practicum.api.exception.userservice.UserNotFoundException;

import java.util.Collection;
import java.util.List;

public interface EventService {
    EventDto createEvent(Long initiatorId, CreateEventDto createEventDto) throws InvalidEventDateException;

    Collection<EventDto> getEvents(Long initiatorId, int from, int size) throws UserNotFoundException;

    Collection<EventShortDto> getEvents(Collection<Long> eventIds);

    Collection<EventDto> getEvents(EventSearch search);

    Collection<EventShortDto> getPublishedEvents(EventSearch search);

    EventDto getEvent(Long initiatorId, Long eventId) throws AccessToEventForbiddenException, EventNotFoundException;

    EventDto getPublishedEventById(Long eventId) throws EventNotFoundException;

    EventDto updateEvent(Long eventId, UpdateEventDto updateEventDto) throws CategoryNotFoundException, EventEditingException, EventNotFoundException, InvalidEventDateException;

    EventDto updateEvent(Long initiatorId, Long eventId, UpdateEventDto updateEventDto) throws AccessToEventForbiddenException, CategoryNotFoundException, EventEditingException, EventNotFoundException, InvalidEventDateException;

    boolean isEventExists(Long eventId);

    boolean isEventsWithCategoryExists(Long categoryId);

    boolean isEventPublished(Long eventId) throws EventNotFoundException;

    void confirmParticipation(Long eventId) throws EventNotFoundException;

    void rejectParticipation(Long eventId) throws EventNotFoundException;

    void checkUserRegistrationAtEvent(Long userId, Long eventId) throws EventNotFoundException, UserNotFoundException, UserNotVisitedEventException;

    List<EventDto> getRecommendationsForUser(Long userId, Integer maxResult);
}
