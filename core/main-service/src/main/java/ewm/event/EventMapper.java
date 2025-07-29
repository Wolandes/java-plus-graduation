package ewm.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(target = "category.id", source = "category")
    @Mapping(target = "createdOn", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "state", expression = "java(EventState.PENDING)")
    Event toEvent(CreateEventDto createEventDto);

    @Mapping(target = "views", ignore = true)
    EventDto toEventDto(Event event);

    @Mapping(target = "views", ignore = true)
    EventShortDto toEventShortDto(Event event);

    Collection<EventDto> toEventDtoCollection(Collection<Event> events);

    Collection<EventShortDto> toEventShortDtoCollection(Collection<Event> events);
}
