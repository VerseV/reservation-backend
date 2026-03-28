package com.devjunior.adrielespejo.reservation_backend.controller;

import com.devjunior.adrielespejo.reservation_backend.dto.ReservationRequestDto;
import com.devjunior.adrielespejo.reservation_backend.model.entity.Reservation;
import com.devjunior.adrielespejo.reservation_backend.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> listAll() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@Valid @RequestBody ReservationRequestDto request) {
        Reservation created = reservationService.createReservation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }
}
