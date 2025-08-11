package ru.practicum.api.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.api.dto.category.CategoryDto;
import ru.practicum.api.dto.user.UserDto;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
public class EventDto {
    private Long id;

    private LocalDateTime createdOn;

    private UserDto initiator;

    private String title;

    private String annotation;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;

    private CategoryDto category;

    private LocationDto location;

    private LocalDateTime publishedOn;

    private boolean paid;

    private int participantLimit;

    private boolean requestModeration;

    private int confirmedRequests;

    private EventState state;

    private long views;
}
