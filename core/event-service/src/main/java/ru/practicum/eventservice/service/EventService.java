package ru.practicum.eventservice.service;

import ru.practicum.eventservice.model.EventSearch;
import ru.practicum.interactionapi.dto.eventservice.CreateEventDto;
import ru.practicum.interactionapi.dto.eventservice.EventDto;
import ru.practicum.interactionapi.dto.eventservice.EventShortDto;
import ru.practicum.interactionapi.dto.eventservice.UpdateEventDto;
import ru.practicum.interactionapi.exception.categoryservice.CategoryNotFoundException;
import ru.practicum.interactionapi.exception.eventservice.AccessToEventForbiddenException;
import ru.practicum.interactionapi.exception.eventservice.EventEditingException;
import ru.practicum.interactionapi.exception.eventservice.EventNotFoundException;
import ru.practicum.interactionapi.exception.eventservice.InvalidEventDateException;
import ru.practicum.interactionapi.exception.userservice.UserNotFoundException;

import java.util.Collection;

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
}
