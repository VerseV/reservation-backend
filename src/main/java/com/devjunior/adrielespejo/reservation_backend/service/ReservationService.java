package com.devjunior.adrielespejo.reservation_backend.service;

import com.devjunior.adrielespejo.reservation_backend.dto.ReservationRequestDto;
import com.devjunior.adrielespejo.reservation_backend.exception.BusinessRuleViolationException;
import com.devjunior.adrielespejo.reservation_backend.model.entity.Reservation;
import com.devjunior.adrielespejo.reservation_backend.model.entity.ReservationStatus;
import com.devjunior.adrielespejo.reservation_backend.repository.ReservationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Transactional
    public Reservation createReservation(ReservationRequestDto request) {
        if (reservationRepository.existsByDateAndTimeAndStatus(
                request.getDate(), request.getTime(), ReservationStatus.ACTIVE)) {
            throw new BusinessRuleViolationException(
                    "A reservation already exists for the same date and time.",
                    HttpStatus.CONFLICT);
        }

        Reservation reservation =
                Reservation.builder()
                        .customerName(request.getCustomerName())
                        .date(request.getDate())
                        .time(request.getTime())
                        .service(request.getService())
                        .status(ReservationStatus.ACTIVE)
                        .build();

        return reservationRepository.save(reservation);
    }

    @Transactional
    public void cancelReservation(Long id) {
        Reservation reservation =
                reservationRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new BusinessRuleViolationException(
                                                "Reservation not found with id: " + id,
                                                HttpStatus.NOT_FOUND));

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new BusinessRuleViolationException(
                    "Reservation is already cancelled.", HttpStatus.BAD_REQUEST);
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }
}
