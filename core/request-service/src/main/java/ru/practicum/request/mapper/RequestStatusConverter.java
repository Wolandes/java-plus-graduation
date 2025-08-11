package ru.practicum.request.mapper;

import jakarta.persistence.AttributeConverter;
import ru.practicum.api.dto.request.RequestStatus;

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
