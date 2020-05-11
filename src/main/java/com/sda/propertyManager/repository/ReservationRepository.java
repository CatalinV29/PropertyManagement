package com.sda.propertyManager.repository;

import com.sda.propertyManager.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation , Integer> {

    Reservation findReservationByStartDate (String startDate);

    Reservation findReservationByEndDate (String endDate);

    Reservation findReservationByReservationId (Integer id);

    Reservation findReservationByClientRating(String clientRating);

    Reservation findReservationByPropertyRating (String propertyRating);

}
