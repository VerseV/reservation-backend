package com.devjunior.adrielespejo.reservation_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for creating a new reservation.
 *
 * @param customerName the name of the customer making the reservation
 * @param date         the date of the reservation
 * @param time         the time of the reservation
 * @param service      the service requested
 */
public record ReservationRequestDto(
        @NotBlank String customerName,
        @NotNull LocalDate date,
        @NotNull LocalTime time,
        @NotBlank String service
) {}