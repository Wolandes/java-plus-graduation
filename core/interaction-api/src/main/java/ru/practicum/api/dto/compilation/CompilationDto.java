package ru.practicum.api.dto.compilation;

import lombok.Builder;
import lombok.Data;
import ru.practicum.api.dto.event.EventShortDto;

import java.util.Collection;

@Builder(toBuilder = true)
@Data
public class CompilationDto {
    private Long id;

    private String title;

    private Collection<EventShortDto> events;

    private boolean pinned;
}
