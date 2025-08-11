package ru.practicum.compilation.controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.service.CompilationService;
import ru.practicum.api.dto.compilation.CompilationDto;
import ru.practicum.api.exception.compilation.CompilationNotFoundException;

import java.util.Collection;

@RequestMapping("/compilations")
@RequiredArgsConstructor
@RestController
@Slf4j
public class PublicCompilationController {
    private final CompilationService compilationService;

    @GetMapping
    public Collection<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                      @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                      @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Get {} compilations starts from {}, pinned: {}", size, from, pinned);
        return compilationService.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compilationId}")
    public CompilationDto getCompilationById(@PathVariable @Positive Long compilationId) throws CompilationNotFoundException {
        log.info("Get compilation with id = {}", compilationId);
        return compilationService.getCompilationById(compilationId);
    }
}
