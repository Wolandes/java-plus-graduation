package ru.practicum.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
public class UpdateEventRequestsStatusDto {
    @NotNull(message = "Список заявок на участие не может быть пустым")
    @Size(min = 1, message = "Кол-во заявок на участие должно быть больше нуля")
    private Collection<Long> requestIds;

    @NotNull(message = "Статус заявки на участие не может быть пустым")
    private RequestStatus status;
}
