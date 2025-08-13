package ru.practicum.requestservice.mapper.converter;

import jakarta.persistence.AttributeConverter;
import ru.practicum.interactionapi.dto.requestservice.RequestStatus;

public class RequestStatusConverter implements AttributeConverter<RequestStatus, String> {
    @Override
    public String convertToDatabaseColumn(RequestStatus requestStatus) {
        return requestStatus.name();
    }

    @Override
    public RequestStatus convertToEntityAttribute(String s) {
        return RequestStatus.valueOf(s);
    }
}
