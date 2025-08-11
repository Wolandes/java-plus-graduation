package ru.practicum.api.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
public class CreateEventDto {
    @Length(min = 3, message = "Заголовок события не может быть меньше 3 символов")
    @Length(max = 120, message = "Заголовок события не может быть больше 120 символов")
    @NotBlank
    private String title;

    @Length(min = 20, message = "Краткое описание события не может быть меньше 20 символов")
    @Length(max = 2000, message = "Краткое описание события не может быть больше 2000 символов")
    @NotBlank
    private String annotation;

    @Length(min = 20, message = "Полное описание события не может быть меньше 20 символов")
    @Length(max = 7000, message = "Полное описание события не может быть больше 7000 символов")
    @NotBlank
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;

    @NotNull
    private Long category;

    @NotNull
    private LocationDto location;

    private boolean paid;

    @PositiveOrZero
    private int participantLimit;

    private boolean requestModeration = true;
}
