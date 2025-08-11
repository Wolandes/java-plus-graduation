package ru.practicum.api.dto.compilation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
public class UpdateCompilationDto {
    @Length(min = 1, message = "Наименование подборки не может быть меньше 1")
    @Length(max = 50, message = "Наименование подборки не может быть больше 50")
    private String title;

    private Set<Long> events;

    private Boolean pinned;
}
