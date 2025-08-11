package ru.practicum.api.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class UpdateEventDto {
    @Length(min = 3, message = "Заголовок события не может быть меньше 3 символов")
    @Length(max = 120, message = "Заголовок события не может быть больше 120 символов")
    private String title;

    @Length(min = 20, message = "Краткое описание события не может быть меньше 20 символов")
    @Length(max = 2000, message = "Краткое описание события не может быть больше 2000 символов")
    private String annotation;

    @Length(min = 20, message = "Полное описание события не может быть меньше 20 символов")
    @Length(max = 7000, message = "Полное описание события не может быть больше 7000 символов")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;

    private Long category;

    private LocationDto location;

    private Boolean paid;

    @PositiveOrZero
    private Integer participantLimit;

    private Boolean requestModeration;

    private EventStateAction stateAction;
}
