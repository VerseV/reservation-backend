package com.devjunior.adrielespejo.reservation_backend.repository;

import com.devjunior.adrielespejo.reservation_backend.model.entity.Reservation;
import com.devjunior.adrielespejo.reservation_backend.model.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByDateAndTime(LocalDate date, LocalTime time);

    boolean existsByDateAndTimeAndStatus(
            LocalDate date, LocalTime time, ReservationStatus status);
}
