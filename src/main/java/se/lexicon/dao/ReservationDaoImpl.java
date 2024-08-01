package se.lexicon.dao;

import se.lexicon.model.Reservation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ReservationDaoImpl implements ReservationDao {

    private final List<Reservation> storage = new ArrayList<>();

    @Override
    public Reservation create(Reservation reservation) {
        validateReservation(reservation);
        for(Reservation reservationElement : storage) {
            if(reservationElement.equals(reservation)) {
                throw new IllegalArgumentException("Reservation is already present");
            }
        }
        storage.add(reservation);
        System.out.println(storage);
        return reservation;
    }

    @Override
    public Optional<Reservation> find(String id) {
        validateParams(id, "Reservation Id");
        for(Reservation reservation : storage) {
            if(reservation.getId().equalsIgnoreCase(id)) {
                return Optional.of(reservation);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public boolean remove(String reservationId) {
        Optional<Reservation> reservationOptional = find(reservationId);
        if(reservationOptional.isPresent()) {
            storage.remove(reservationOptional.get());
            System.out.println("After removing: " + storage);
            return true;
        }
        return false;
    }

    @Override
    public void update(Reservation reservation) {
        validateReservation(reservation);
        boolean isUpdated = false;
        for(Reservation reservationElement : storage) {
            if(reservationElement.getId().equalsIgnoreCase(reservation.getId())) {
                if(reservation.getStartTime() != null)
                    reservationElement.setStartTime(reservation.getStartTime());
                if(reservation.getEndTime() != null)
                    reservationElement.setEndTime(reservation.getEndTime());
                if(reservation.getAssociatedVehicle() != null)
                    reservationElement.setAssociatedVehicle(reservation.getAssociatedVehicle());
                if(reservation.getParkingSpot() != null)
                    reservationElement.setParkingSpot(reservation.getParkingSpot());
                isUpdated = true;
                System.out.println("After Update: " + storage);
                break;
            }
        }
        if(!isUpdated)
            throw new IllegalArgumentException("Reservation is not found... Enter correct Reservation id...");
    }

    @Override
    public Collection<Reservation> findByLicensePlate(String licensePlate) {
        validateParams(licensePlate, "License Plate");
        Collection<Reservation> reservationList = new ArrayList<>();
        for(Reservation reservation : storage) {
            if(reservation.getAssociatedVehicle() != null) {
                if(reservation.getAssociatedVehicle().getLicensePlate().equalsIgnoreCase(licensePlate)) {
                    reservationList.add(reservation);
                }
            }
        }
        return reservationList;
    }

    private void validateReservation(Reservation reservation) {
        if(reservation == null)
            throw new NullPointerException("Reservation cannot be null...");
    }

    private void validateParams(String param, String paramName) {
        if (param == null)
            throw new NullPointerException(paramName + " cannot be null...");
    }
}
