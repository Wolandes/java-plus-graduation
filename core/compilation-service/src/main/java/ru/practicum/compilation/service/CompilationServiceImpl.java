package ru.practicum.compilation.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.model.QCompilation;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.api.dto.compilation.CompilationDto;
import ru.practicum.api.dto.compilation.CreateCompilationDto;
import ru.practicum.api.dto.compilation.UpdateCompilationDto;
import ru.practicum.api.exception.compilation.CompilationNotFoundException;
import ru.practicum.api.pageable.PageOffset;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public CompilationDto getCompilationById(Long compilationId) throws CompilationNotFoundException {
        return compilationMapper.mapToCompilationDto(compilationRepository.findById(compilationId).orElseThrow(() -> new CompilationNotFoundException(compilationId)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompilationDto updateCompilation(Long compilationId, UpdateCompilationDto updateCompilationDto) throws CompilationNotFoundException {
        Compilation compilation = compilationRepository.findById(compilationId).orElseThrow(() -> new CompilationNotFoundException(compilationId));

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

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCompilation(Long compilationId) throws CompilationNotFoundException {
        if (!compilationRepository.existsById(compilationId)) {
            throw new CompilationNotFoundException(compilationId);
        }

        compilationRepository.deleteById(compilationId);
    }
}
