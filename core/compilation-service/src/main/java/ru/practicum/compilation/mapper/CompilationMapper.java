package ru.practicum.compilation.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.api.client.EventServiceClient;
import ru.practicum.api.dto.compilation.CompilationDto;
import ru.practicum.api.dto.compilation.CreateCompilationDto;
import ru.practicum.compilation.model.Compilation;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class CompilationMapper {
    private final EventServiceClient eventServiceClient;

    public Compilation mapToCompilation(CreateCompilationDto createCompilationDto) {
        return Compilation.builder()
                .title(createCompilationDto.getTitle())
                .events(createCompilationDto.getEvents())
                .pinned(createCompilationDto.getPinned())
                .build();
    }

    public CompilationDto mapToCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .events(compilation.getEvents() != null ? eventServiceClient.getEvents(compilation.getEvents()) : null)
                .pinned(compilation.isPinned())
                .build();
    }

    public Collection<CompilationDto> mapToCompilationDtoCollection(Collection<Compilation> compilations) {
        return compilations.stream().map(this::mapToCompilationDto).toList();
    }
}
