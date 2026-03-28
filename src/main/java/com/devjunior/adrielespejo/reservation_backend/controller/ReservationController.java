package com.devjunior.adrielespejo.reservation_backend.controller;

import com.devjunior.adrielespejo.reservation_backend.dto.ReservationRequestDto;
import com.devjunior.adrielespejo.reservation_backend.model.entity.Reservation;
import com.devjunior.adrielespejo.reservation_backend.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller that exposes endpoints for managing reservations.
 */
@RestController
@RequestMapping("/reservas")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * Retrieves all reservations.
     *
     * @return list of all reservations with HTTP 200
     */
    @GetMapping
    public ResponseEntity<List<Reservation>> listAll() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    /**
     * Creates a new reservation.
     *
     * @param request the reservation data from the request body
     * @return the created reservation with HTTP 201
     */
    @PostMapping
    public ResponseEntity<Reservation> create(@Valid @RequestBody ReservationRequestDto request) {
        Reservation created = reservationService.createReservation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Cancels a reservation by its id.
     *
     * @param id the id of the reservation to cancel
     * @return HTTP 204 if cancelled successfully
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }
}