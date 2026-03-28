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

/**
 * Service layer for managing reservations.
 * Contains all business logic related to reservation operations.
 */
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    /**
     * Retrieves all reservations stored in the database.
     *
     * @return list of all reservations
     */
    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    /**
     * Creates a new reservation if no active reservation exists for the same date and time.
     *
     * @param request the DTO containing the reservation details
     * @return the newly created reservation
     * @throws BusinessRuleViolationException if a reservation already exists for the given date and time
     */
    @Transactional
    public Reservation createReservation(ReservationRequestDto request) {
        if (reservationRepository.existsByDateAndTimeAndStatus(
                request.date(), request.time(), ReservationStatus.ACTIVE)) {
            throw new BusinessRuleViolationException(
                    "A reservation already exists for the same date and time.",
                    HttpStatus.CONFLICT);
        }

        Reservation reservation = Reservation.builder()
                .customerName(request.customerName())
                .date(request.date())
                .time(request.time())
                .service(request.service())
                .status(ReservationStatus.ACTIVE)
                .build();

        return reservationRepository.save(reservation);
    }

    /**
     * Cancels an existing active reservation by its id.
     *
     * @param id the id of the reservation to cancel
     * @throws BusinessRuleViolationException if the reservation is not found or is already cancelled
     */
    @Transactional
    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleViolationException(
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