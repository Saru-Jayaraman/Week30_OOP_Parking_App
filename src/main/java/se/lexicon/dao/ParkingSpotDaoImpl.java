package se.lexicon.dao;

import se.lexicon.model.ParkingSpot;

import java.util.*;

public class ParkingSpotDaoImpl implements ParkingSpotDao {

    private final List<ParkingSpot> storage = new ArrayList<>();

    @Override
    public ParkingSpot create(ParkingSpot parkingSpot) {
        validateParkingSpot(parkingSpot);
        for(ParkingSpot parkingSpotElement : storage) {
            if(parkingSpotElement.getSpotNumber() == parkingSpot.getSpotNumber() &&
                    parkingSpotElement.getAreaCode() == parkingSpot.getAreaCode()) {
                throw new IllegalArgumentException("Parking spot is already present...");
            }
        }
        storage.add(parkingSpot);
        System.out.println(storage);
        return parkingSpot;
    }

    @Override
    public boolean remove(int spotNumber, int areaCode) {
        Optional<ParkingSpot> parkingSpotOptional = find(spotNumber, areaCode);
        if(parkingSpotOptional.isPresent()) {
            storage.remove(parkingSpotOptional.get());
            System.out.println("After removing: " + storage);
            return true;
        }
        return false;
    }

    @Override
    public Optional<ParkingSpot> find(int spotNumber, int areaCode) {
        validateId(spotNumber, "Spot Number");
        validateId(areaCode, "Area code");
        for(ParkingSpot parkingSpot : storage) {
            if(parkingSpot.getSpotNumber() == spotNumber && parkingSpot.getAreaCode() == areaCode) {
                return Optional.of(parkingSpot);
            }
        }
        return Optional.empty();
    }

    @Override
    public Collection<ParkingSpot> findAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public Collection<ParkingSpot> findByAreaCode(int areaCode) {
        validateId(areaCode, "Area code");
        Collection<ParkingSpot> parkingSpotsList = new ArrayList<>();
        for(ParkingSpot parkingSpot : storage) {
            if(parkingSpot.getAreaCode() == areaCode) {
                parkingSpotsList.add(parkingSpot);
            }
        }
        return parkingSpotsList;
    }

    @Override
    public void occupyParkingSpot(int spotNumber, int areaCode) {
        Optional<ParkingSpot> parkingSpotOptional = find(spotNumber, areaCode);
        parkingSpotOptional.ifPresent(ParkingSpot::occupy);
    }

    @Override
    public void vacateParkingSpot(int spotNumber, int areaCode) {
        Optional<ParkingSpot> parkingSpotOptional = find(spotNumber, areaCode);
        parkingSpotOptional.ifPresent(ParkingSpot::vacate);
    }

    private void validateParkingSpot(ParkingSpot parkingSpot) {
        if(parkingSpot == null)
            throw new NullPointerException("Parking spot cannot be null...");
    }

    private void validateId(int id, String paramName) {
        if(id <= 0)
            throw new IllegalArgumentException(paramName + " cannot be zero or negative number...");
    }
}
