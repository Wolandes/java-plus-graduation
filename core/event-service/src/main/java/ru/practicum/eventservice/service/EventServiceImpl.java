package ru.practicum.eventservice.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import ewm.client.AnalyzerClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.practicum.api.client.RequestServiceClient;
import ru.practicum.api.dto.requestservice.RequestDto;
import ru.practicum.api.dto.requestservice.RequestStatus;
import ru.practicum.api.exception.RemoteServiceException;
import ru.practicum.api.exception.eventservice.*;
import ru.practicum.eventservice.model.Event;
import ru.practicum.eventservice.model.EventSearch;
import ru.practicum.eventservice.model.Location;
import ru.practicum.eventservice.model.QEvent;
import ru.practicum.eventservice.repository.EventRepository;
import ru.practicum.eventservice.mapper.EventMapper;
import ru.practicum.api.dto.categoryservice.CategoryDto;
import ru.practicum.api.dto.eventservice.*;
import ru.practicum.api.dto.userservice.UserDto;
import ru.practicum.api.exception.categoryservice.CategoryNotFoundException;
import ru.practicum.api.exception.userservice.UserNotFoundException;
import ru.practicum.api.client.CategoryServiceClient;
import ru.practicum.api.client.UserServiceClient;
import ru.practicum.api.pageable.PageOffset;
import ru.practicum.grpc.stats.recommendation.RecommendedEventProto;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    private final EventMapper eventMapper;

    private final CategoryServiceClient categoryServiceClient;

    private final UserServiceClient userServiceClient;

    private final RequestServiceClient requestServiceClient;

    private final AnalyzerClient analyzerClient;


    @Override
    public EventDto createEvent(Long initiatorId, CreateEventDto createEventDto) throws InvalidEventDateException {
        if (createEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new InvalidEventDateException("Дата и время события должна быть больше текущих даты и времени не менее, чем на 2 часа");
        }

        if (!userServiceClient.isUserExists(initiatorId)) {
            throw new UserNotFoundException(initiatorId);
        }
        if (!categoryServiceClient.isCategoryExists(createEventDto.getCategory())) {
            throw new CategoryNotFoundException(createEventDto.getCategory());
        }

        UserDto initiator = userServiceClient.getUser(initiatorId);
        CategoryDto category = categoryServiceClient.getCategory(createEventDto.getCategory());

        Event event = eventMapper.mapToEvent(createEventDto, initiator.getId(), category.getId());
        eventRepository.save(event);

        return eventMapper.mapToEventDto(event, initiator, category);
    }

    @Override
    public Collection<EventDto> getEvents(Long initiatorId, int from, int size) throws UserNotFoundException {
        if (!userServiceClient.isUserExists(initiatorId)) {
            throw new UserNotFoundException(initiatorId);
        }

        UserDto initiator = userServiceClient.getUser(initiatorId);

        Predicate predicate = QEvent.event.initiatorId.eq(initiator.getId());
        PageOffset pageOffset = PageOffset.of(from, size, Sort.by("id").ascending());

        return eventMapper.mapToEventDtoCollection(eventRepository.findAll(predicate, pageOffset).getContent(), initiator);
    }

    @Override
    public Collection<EventShortDto> getEvents(Collection<Long> eventIds) {
        return eventMapper.mapToEventShortDtoCollection(eventRepository.findAllById(eventIds));
    }

    @Override
    public Collection<EventDto> getEvents(EventSearch search) {
        return eventMapper.mapToEventDtoCollection(getEventsByEventSearch(search));
    }

    @Override
    public Collection<EventShortDto> getPublishedEvents(EventSearch search) {
        return eventMapper.mapToEventShortDtoCollection(getEventsByEventSearch(search));
    }

    @Override
    public EventDto getEvent(Long initiatorId, Long eventId) throws AccessToEventForbiddenException, EventNotFoundException {
        Event event = findEvent(eventId);

        if (!Objects.equals(event.getInitiatorId(), initiatorId)) {
            throw new AccessToEventForbiddenException(eventId);
        }

        return eventMapper.mapToEventDto(event, userServiceClient.getUser(initiatorId), categoryServiceClient.getCategory(event.getCategoryId()));
    }

    @Override
    public EventDto getPublishedEventById(Long eventId) throws EventNotFoundException {
        Event event = findEvent(eventId);

        if (!Objects.equals(event.getState(), EventState.PUBLISHED)) {
            throw new EventNotFoundException(eventId);
        }

        return eventMapper.mapToEventDto(event, userServiceClient.getUser(event.getInitiatorId()), categoryServiceClient.getCategory(event.getCategoryId()));
    }

    @Override
    public EventDto updateEvent(Long eventId, UpdateEventDto updateEventDto) throws CategoryNotFoundException, EventEditingException, EventNotFoundException, InvalidEventDateException {
        Event event = findEvent(eventId);

        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new EventEditingException("Нельзя редактировать событие, до наступления которого осталось меньше часа");
        }

        if (updateEventDto.getTitle() != null) {
            event.setTitle(updateEventDto.getTitle());
        }

        if (updateEventDto.getAnnotation() != null) {
            event.setAnnotation(updateEventDto.getAnnotation());
        }

        if (updateEventDto.getDescription() != null) {
            event.setDescription(updateEventDto.getDescription());
        }

        if (updateEventDto.getEventDate() != null) {
            if (updateEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
                throw new InvalidEventDateException("Дата и время события должна быть больше текущих даты и времени не менее, чем на 2 часа");
            }

            event.setEventDate(updateEventDto.getEventDate());
        }

        if (updateEventDto.getCategory() != null) {
            if (!categoryServiceClient.isCategoryExists(updateEventDto.getCategory())) {
                throw new CategoryNotFoundException(updateEventDto.getCategory());
            }

            event.setCategoryId(updateEventDto.getCategory());
        }

        if (updateEventDto.getLocation() != null) {
            event.setLocation(Location.builder().lat(updateEventDto.getLocation().getLat()).lon(updateEventDto.getLocation().getLon()).build());
        }

        if (updateEventDto.getPaid() != null) {
            event.setPaid(updateEventDto.getPaid());
        }

        if (updateEventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventDto.getParticipantLimit());
        }

        if (updateEventDto.getRequestModeration() != null) {
            event.setRequestModeration(updateEventDto.getRequestModeration());
        }

        if (updateEventDto.getStateAction() != null) {
            if (updateEventDto.getStateAction() == EventStateAction.PUBLISH_EVENT) {
                if (event.getState() != EventState.PENDING) {
                    throw new EventEditingException("Cannot publish the event because it's not in the right state: PENDING");
                }
                event.setState(EventState.PUBLISHED);
            } else if (updateEventDto.getStateAction() == EventStateAction.REJECT_EVENT) {
                if (event.getState() == EventState.PUBLISHED) {
                    throw new EventEditingException("Cannot reject the event because it's in the state: PUBLISHED");
                }
                event.setState(EventState.REJECTED);
            }
        }

        return eventMapper.mapToEventDto(eventRepository.save(event), userServiceClient.getUser(event.getInitiatorId()), categoryServiceClient.getCategory(event.getCategoryId()));
    }

    @Override
    public EventDto updateEvent(Long initiatorId, Long eventId, UpdateEventDto updateEventDto) throws AccessToEventForbiddenException, CategoryNotFoundException, EventEditingException, EventNotFoundException, InvalidEventDateException {
        Event event = findEvent(eventId);

        if (!Objects.equals(event.getInitiatorId(), initiatorId)) {
            throw new AccessToEventForbiddenException(eventId);
        }

        if (Objects.equals(event.getState(), EventState.PUBLISHED)) {
            throw new EventEditingException(eventId);
        }

        if (updateEventDto.getTitle() != null) {
            event.setTitle(updateEventDto.getTitle());
        }

        if (updateEventDto.getAnnotation() != null) {
            event.setAnnotation(updateEventDto.getAnnotation());
        }

        if (updateEventDto.getDescription() != null) {
            event.setDescription(updateEventDto.getDescription());
        }

        if (updateEventDto.getEventDate() != null) {
            if (updateEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
                throw new InvalidEventDateException("Дата и время события должна быть больше текущих даты и времени не менее, чем на 2 часа");
            }

            event.setEventDate(updateEventDto.getEventDate());
        }

        if (updateEventDto.getCategory() != null) {
            if (!categoryServiceClient.isCategoryExists(updateEventDto.getCategory())) {
                throw new CategoryNotFoundException(updateEventDto.getCategory());
            }

            event.setCategoryId(updateEventDto.getCategory());
        }

        if (updateEventDto.getLocation() != null) {
            event.setLocation(Location.builder().lat(updateEventDto.getLocation().getLat()).lon(updateEventDto.getLocation().getLon()).build());
        }

        if (updateEventDto.getPaid() != null) {
            event.setPaid(updateEventDto.getPaid());
        }

        if (updateEventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventDto.getParticipantLimit());
        }

        if (updateEventDto.getRequestModeration() != null) {
            event.setRequestModeration(updateEventDto.getRequestModeration());
        }

        if (updateEventDto.getStateAction() != null) {
            if (event.getState() == EventState.PUBLISHED) {
                throw new EventEditingException("Only pending or canceled events can be changed");
            }

            if (updateEventDto.getStateAction() == EventStateAction.SEND_TO_REVIEW) {
                event.setState(EventState.PENDING);
            } else if (updateEventDto.getStateAction() == EventStateAction.CANCEL_REVIEW) {
                event.setState(EventState.CANCELED);
            }
        }

        return eventMapper.mapToEventDto(eventRepository.save(event), userServiceClient.getUser(initiatorId), categoryServiceClient.getCategory(event.getCategoryId()));
    }

    @Override
    public boolean isEventExists(Long eventId) {
        return eventRepository.existsById(eventId);
    }

    @Override
    public boolean isEventsWithCategoryExists(Long categoryId) {
        return eventRepository.existsByCategoryId(categoryId);
    }

    @Override
    public boolean isEventPublished(Long eventId) throws EventNotFoundException {
        return findEvent(eventId).getState().equals(EventState.PUBLISHED);
    }

    @Override
    public void confirmParticipation(Long eventId) throws EventNotFoundException {
        Event event = findEvent(eventId);
        event.setConfirmedRequests(event.getConfirmedRequests() + 1);

        eventRepository.save(event);
    }

    @Override
    public void rejectParticipation(Long eventId) throws EventNotFoundException {
        Event event = findEvent(eventId);
        event.setConfirmedRequests(event.getConfirmedRequests() - 1);

        eventRepository.save(event);
    }

    private Collection<Event> getEventsByEventSearch(EventSearch search) {
        QEvent event = QEvent.event;

        BooleanBuilder predicate = new BooleanBuilder();

        if (search.getText() != null && !search.getText().isBlank()) {
            predicate.and(event.annotation.contains(search.getText()).or(event.description.contains(search.getText())));
        }

        if (search.getUsers() != null && !search.getUsers().isEmpty()) {
            predicate.and(event.initiatorId.in(search.getUsers()));
        }

        if (search.getStates() != null && !search.getStates().isEmpty()) {
            predicate.and(event.state.in(search.getStates()));
        }

        if (search.getCategories() != null && !search.getCategories().isEmpty()) {
            predicate.and(event.categoryId.in(search.getCategories()));
        }

        if (search.getPaid() != null) {
            predicate.and(event.paid.eq(search.getPaid()));
        }

        if (search.getRangeStart() != null) {
            predicate.and(event.eventDate.after(search.getRangeStart()));
        }

        if (search.getRangeEnd() != null) {
            predicate.and(event.eventDate.before(search.getRangeEnd()));
        }

        if (search.isOnlyAvailable()) {
            predicate.and(event.participantLimit.eq(0).or(event.confirmedRequests.lt(event.participantLimit)));
        }

        Pageable pageable = PageOffset.of(search.getFrom(), search.getSize());
        if (search.getSort() != null) {
            switch (search.getSort()) {
                case EventSort.EVENT_DATE ->
                        pageable = PageOffset.of(search.getFrom(), search.getSize(), Sort.Direction.ASC, "eventDate");
                case EventSort.VIEWS ->
                        pageable = PageOffset.of(search.getFrom(), search.getSize(), Sort.Direction.ASC, "views");
            }
        }

        List<Event> result = new ArrayList<>();
        eventRepository.findAll(predicate, pageable).forEach(result::add);
        return result;
    }

    @Override
    public void checkUserRegistrationAtEvent(Long userId, Long eventId) throws EventNotFoundException, UserNotFoundException, UserNotVisitedEventException {
        Event event = findEvent(eventId);
        findUserDto(userId);
        if (event.getState() != EventState.PUBLISHED) {
            throw new EventNotFoundException(eventId);
        }
        if (event.isRequestModeration() || event.getParticipantLimit() != 0) {
            if (event.getInitiatorId() != userId && requestServiceClient.findByRequesterIdAndEventId(userId, eventId)
                    .filter(o -> o.getStatus() == RequestStatus.CONFIRMED).isEmpty()) {
                throw new UserNotVisitedEventException("Пользователь с id=" + userId + " не посещал событие с id=" + event);
            }
        }
    }

    @Override
    public List<EventDto> getRecommendationsForUser(Long userId, Integer maxResult) {
        findUserDto(userId);
        List<Long> eventIds = analyzerClient.getRecommendationsForUser(userId, maxResult)
                .map(RecommendedEventProto::getEventId).toList();
        List<Event> events = eventRepository.findByIdIn(eventIds);
        Set<Long> initiatorIds = events.stream().map(Event::getInitiatorId).collect(Collectors.toSet());
        Map<Long, UserDto> initiatorsMap = findUsers(initiatorIds.stream().toList()).stream()
                .collect(Collectors.toMap(UserDto::getId, Function.identity()));
        List<EventDto> eventsDto = new ArrayList<>(eventMapper.mapToEventDtoCollection(events));
        loadStatisticAndRequestForList(eventsDto);
        return eventsDto;
    }

    private Event findEvent(Long eventId) throws EventNotFoundException {
        return eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
    }

    private UserDto findUserDto(Long userId) throws UserNotFoundException {
        return userServiceClient.getUser(userId);
    }

    private List<UserDto> findUsers(List<Long> usersId) {
        try {
            return userServiceClient.getUsers(usersId).stream().toList();
        } catch (FeignException ex) {
            if (ex.status() == HttpStatus.NOT_FOUND.value()) {
                throw new UserNotFoundException(usersId.getFirst());
            }
            throw new RemoteServiceException("Error in the remote service 'user-service");
        }
    }

    private List<EventDto> loadStatisticAndRequestForList(List<EventDto> events) {
        if (events == null || events.isEmpty()) {
            return List.of();
        }

        List<RequestDto> requests = requestServiceClient.findByEventIdInAndStatus(events.stream()
                .map(EventDto::getId)
                .toList(), RequestStatus.CONFIRMED);

        Map<Long, Double> ratingsMap = analyzerClient.getInteractionsCount(events.stream().map(EventDto::getId).collect(Collectors.toList()));

        return events.stream()
                .peek(event -> event.setConfirmedRequests(
                        (int) requests.stream()
                                .filter(request -> request.getEvent().equals(event.getId()))
                                .count()
                ))
                .peek(event -> event.setRating(ratingsMap.getOrDefault(event.getId(), 0.0)))
                .toList();
    }
}
