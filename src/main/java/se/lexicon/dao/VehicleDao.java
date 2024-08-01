package se.lexicon.dao;

import se.lexicon.model.Vehicle;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface VehicleDao {
    List<Vehicle> findAll();

    Optional<Vehicle> find(String licensePlate, int customerId);

    Vehicle create(Vehicle vehicle);

    boolean remove(String licensePlate, int customerId);

    void update(Vehicle vehicle);

    Collection<Vehicle> findByCustomerId(int customerId);
}
