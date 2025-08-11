package ru.practicum.compilation.service;

import ru.practicum.api.dto.compilation.CompilationDto;
import ru.practicum.api.dto.compilation.CreateCompilationDto;
import ru.practicum.api.dto.compilation.UpdateCompilationDto;

import java.util.Collection;

public interface CompilationService {
    CompilationDto createCompilation(CreateCompilationDto createCompilationDto);

    Collection<CompilationDto> getCompilations(Boolean pinned, int from, int size);

    CompilationDto getCompilationById(Long compilationId);

    CompilationDto updateCompilation(Long compilationId, UpdateCompilationDto updateCompilationDto);

    void deleteCompilation(Long compilationId);
}
