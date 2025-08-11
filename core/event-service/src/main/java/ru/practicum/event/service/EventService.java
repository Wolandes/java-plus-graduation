package ru.practicum.event.service;

import ru.practicum.event.model.EventSearch;
import ru.practicum.api.dto.event.CreateEventDto;
import ru.practicum.api.dto.event.EventDto;
import ru.practicum.api.dto.event.EventShortDto;
import ru.practicum.api.dto.event.UpdateEventDto;

import java.util.Collection;

public interface EventService {
    EventDto createEvent(Long initiatorId, CreateEventDto createEventDto);


    Collection<EventDto> getEvents(Long initiatorId, int from, int size);

    Collection<EventShortDto> getEvents(Collection<Long> eventIds);

    Collection<EventDto> getEvents(EventSearch search);

    Collection<EventShortDto> getPublishedEvents(EventSearch search);

    EventDto getEvent(Long initiatorId, Long eventId);

    EventDto getPublishedEventById(Long eventId);

    EventDto updateEvent(Long eventId, UpdateEventDto updateEventDto);

    EventDto updateEvent(Long initiatorId, Long eventId, UpdateEventDto updateEventDto);

    boolean isEventExists(Long eventId);

    boolean isEventsWithCategoryExists(Long categoryId);

    boolean isEventPublished(Long eventId);

    void confirmParticipation(Long eventId);

    void rejectParticipation(Long eventId);
}
