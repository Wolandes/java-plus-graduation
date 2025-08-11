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
public class EventShortDto {
    private Long id;

    private UserDto initiator;

    private String title;

    private String annotation;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;

    private CategoryDto category;

    private boolean paid;

    private int confirmedRequests;

    private long views;
}
