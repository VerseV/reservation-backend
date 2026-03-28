package com.devjunior.adrielespejo.reservation_backend.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name", length = 100, nullable = false)
    private String customerName;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "reservation_time", nullable = false)
    private LocalTime time;

    @Column(length = 50, nullable = false)
    private String service;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private ReservationStatus status;
}
