package ru.practicum.compilationservice.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.compilationservice.model.Compilation;
import ru.practicum.compilationservice.model.QCompilation;
import ru.practicum.compilationservice.repository.CompilationRepository;
import ru.practicum.compilationservice.mapper.CompilationMapper;
import ru.practicum.interactionapi.dto.compilationservice.CompilationDto;
import ru.practicum.interactionapi.dto.compilationservice.CreateCompilationDto;
import ru.practicum.interactionapi.dto.compilationservice.UpdateCompilationDto;
import ru.practicum.interactionapi.exception.compilationservice.CompilationNotFoundException;
import ru.practicum.interactionapi.pageable.PageOffset;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;

    private final CompilationMapper compilationMapper;

    @Override
    public CompilationDto createCompilation(CreateCompilationDto createCompilationDto) {
        Compilation compilation = compilationMapper.mapToCompilation(createCompilationDto);

        if (createCompilationDto.getEvents() != null) {
            compilation.setEvents(createCompilationDto.getEvents());
        }

        return compilationMapper.mapToCompilationDto(compilationRepository.save(compilation));
    }

    @Override
    public Collection<CompilationDto> getCompilations(Boolean pinned, int from, int size) {
        BooleanBuilder predicate = new BooleanBuilder();
        Pageable pageable = PageOffset.of(from, size);

        if (pinned != null) {
            predicate.and(QCompilation.compilation.pinned.eq(pinned));
        }

        return compilationMapper.mapToCompilationDtoCollection(compilationRepository.findAll(predicate, pageable).getContent());
    }

    @Override
    public CompilationDto getCompilationById(Long compilationId) throws CompilationNotFoundException {
        return compilationMapper.mapToCompilationDto(findCompilation(compilationId));
    }

    @Override
    public CompilationDto updateCompilation(Long compilationId, UpdateCompilationDto updateCompilationDto) throws CompilationNotFoundException {
        Compilation compilation = findCompilation(compilationId);

        if (updateCompilationDto.getTitle() != null) {
            compilation.setTitle(updateCompilationDto.getTitle());
        }

        if (updateCompilationDto.getEvents() != null) {
            compilation.setEvents(updateCompilationDto.getEvents());
        }

        if (updateCompilationDto.getPinned() != null) {
            compilation.setPinned(updateCompilationDto.getPinned());
        }

        return compilationMapper.mapToCompilationDto(compilationRepository.save(compilation));
    }

    @Override
    public void deleteCompilation(Long compilationId) throws CompilationNotFoundException {
        if (!compilationRepository.existsById(compilationId)) {
            throw new CompilationNotFoundException(compilationId);
        }

        compilationRepository.deleteById(compilationId);
    }

    private Compilation findCompilation(Long compilationId) throws CompilationNotFoundException{
        return compilationRepository.findById(compilationId).orElseThrow(() -> new CompilationNotFoundException(compilationId));
    }
}
