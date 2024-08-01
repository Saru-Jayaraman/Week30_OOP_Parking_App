package se.lexicon.dao;

import se.lexicon.model.ParkingSpot;

import java.util.Collection;
import java.util.Optional;

public interface ParkingSpotDao {
    ParkingSpot create(ParkingSpot parkingSpot);

    boolean remove(int spotNumber, int areaCode);

    Optional<ParkingSpot> find(int spotNumber, int areaCode);

    Collection<ParkingSpot> findAll();

    Collection<ParkingSpot> findByAreaCode(int areaCode);

    void occupyParkingSpot(int spotNumber, int areaCode);

    void vacateParkingSpot(int spotNumber, int areaCode);
}