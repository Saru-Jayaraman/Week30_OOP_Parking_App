package se.lexicon.dao;

import se.lexicon.model.Reservation;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ReservationDao {
    Reservation create(Reservation reservation);

    Optional<Reservation> find(String id);

    List<Reservation> findAll();

    boolean remove(String reservationId);

    void update(Reservation reservation);

    Collection<Reservation> findByLicensePlate(String licensePlate);
}