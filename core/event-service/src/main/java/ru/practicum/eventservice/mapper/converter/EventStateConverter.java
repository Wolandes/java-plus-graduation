package ru.practicum.eventservice.mapper.converter;

import jakarta.persistence.AttributeConverter;
import ru.practicum.interactionapi.dto.eventservice.EventState;

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
