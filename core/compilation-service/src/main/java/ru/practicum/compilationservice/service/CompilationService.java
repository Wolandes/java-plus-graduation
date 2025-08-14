package ru.practicum.compilationservice.service;

import ru.practicum.api.dto.compilationservice.CompilationDto;
import ru.practicum.api.dto.compilationservice.CreateCompilationDto;
import ru.practicum.api.dto.compilationservice.UpdateCompilationDto;
import ru.practicum.api.exception.compilationservice.CompilationNotFoundException;

import java.util.Collection;

public interface CompilationService {
    CompilationDto createCompilation(CreateCompilationDto createCompilationDto);

    Collection<CompilationDto> getCompilations(Boolean pinned, int from, int size);

    CompilationDto getCompilationById(Long compilationId) throws CompilationNotFoundException;

    CompilationDto updateCompilation(Long compilationId, UpdateCompilationDto updateCompilationDto) throws CompilationNotFoundException;

    void deleteCompilation(Long compilationId) throws CompilationNotFoundException;
}
