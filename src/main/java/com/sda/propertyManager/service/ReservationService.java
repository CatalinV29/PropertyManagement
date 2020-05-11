package com.sda.propertyManager.service;

import com.sda.propertyManager.model.Reservation;
import com.sda.propertyManager.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    public Reservation findReservationByReservationId(Integer reservationId) throws Exception {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isPresent()) {
            return reservation.get();
        } else {
            throw new Exception("No client record exist for given id");
        }
    }


    public List<Reservation> findAll(Integer page, Integer size) {
        List<Reservation> reservations = new ArrayList<>();
        List<Reservation> dbReservation = reservationRepository.findAll(PageRequest.of(page, size)).getContent();
        for (Reservation reservation : dbReservation) {
            reservations.add(reservation);
        }
        return reservations;
    }

    public Reservation createReservation(Reservation reservation) {
        if (reservation.getReservationId() == null) {
            reservation = reservationRepository.save(reservation);
            return reservation;
        } else {
            Optional<Reservation> optionalReservation = reservationRepository.findById(reservation.getReservationId());
            if (optionalReservation.isPresent()) {
                Reservation newReservation = optionalReservation.get();
                newReservation.setStartDate(reservation.getStartDate());
                newReservation.setEndDate(reservation.getEndDate());
                newReservation.setComments(reservation.getComments());
                newReservation.setClientRating(reservation.getClientRating());
                newReservation.setPropertyRating(reservation.getPropertyRating());
                newReservation.setNumberOfPersons(reservation.getNumberOfPersons());
                return newReservation;
            } else {
                reservation = reservationRepository.save(reservation);
                return reservation;
            }
        }
    }

    public List<Reservation> updateReservation(Integer id, Reservation reservation) throws UserNotFoundException {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isPresent()) {
            Reservation reservation1 = optionalReservation.get();
            reservation1.setStartDate(reservation.getStartDate());
            reservation1.setEndDate(reservation.getEndDate());
            reservation1.setComments(reservation.getComments());
            reservation1.setClientRating(reservation.getClientRating());
            reservation1.setPropertyRating(reservation.getPropertyRating());
            reservation1.setNumberOfPersons(reservation.getNumberOfPersons());
            reservationRepository.save(reservation1);
            List<Reservation> reservationList = new ArrayList<>();
            reservationRepository.findAll().forEach(c -> {
                reservationList.add(c);
            });
            return reservationList;
        } else {
            throw new UserNotFoundException(String.format("No user found with the id: %s!", id));
        }
    }

    public List<Reservation> deleteReservation(Integer reservationId) throws UserNotFoundException {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        if (optionalReservation.isPresent()) {
            reservationRepository.deleteById(reservationId);
            List<Reservation> reservationList = new ArrayList<>();
            reservationRepository.findAll().forEach(c -> {
                reservationList.add(c);
            });
            return reservationList;
        } else {
            throw new UserNotFoundException(String.format("No user found with the id: %s!, id"));
        }
    }

}
