package ru.practicum.event.mapper;

import jakarta.persistence.AttributeConverter;
import ru.practicum.api.dto.event.EventState;

public class EventStateConverter  implements AttributeConverter<EventState, String> {
    @Override
    public String convertToDatabaseColumn(EventState attribute) {
        return attribute.name();
    }

    @Override
    public EventState convertToEntityAttribute(String dbData) {
        return EventState.valueOf(dbData);
    }
}
