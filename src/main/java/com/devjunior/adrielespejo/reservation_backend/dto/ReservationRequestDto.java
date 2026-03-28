package com.devjunior.adrielespejo.reservation_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequestDto {

    @NotBlank private String customerName;
    @NotNull private LocalDate date;
    @NotNull private LocalTime time;
    @NotBlank private String service;
}
