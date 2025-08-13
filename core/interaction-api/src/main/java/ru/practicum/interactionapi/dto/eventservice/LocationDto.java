package ru.practicum.interactionapi.dto.eventservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Широта и долгота места проведения события.
 */
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
public class LocationDto {
    /**
     * Широта.
     */
    private float lat;

    /**
     * Долгота.
     */
    private float lon;
}
