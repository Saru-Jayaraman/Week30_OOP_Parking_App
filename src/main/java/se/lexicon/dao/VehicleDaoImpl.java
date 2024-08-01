package se.lexicon.dao;

import se.lexicon.model.Vehicle;

import java.util.*;

public class VehicleDaoImpl implements VehicleDao {

    private final List<Vehicle> storage = new ArrayList<>();

    @Override
    public List<Vehicle> findAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public Optional<Vehicle> find(String licensePlate, int customerId) {
        validateLicensePlate(licensePlate);
        validateCustomerId(customerId);
        for (Vehicle vehicle : storage) {
            if (vehicle.getLicensePlate().equals(licensePlate) && vehicle.getCustomer().getId() == customerId) {
                return Optional.of(vehicle);
            }
        }
        return Optional.empty();
    }

    @Override
    public Vehicle create(Vehicle vehicle) {
        validateVehicle(vehicle);
        for(Vehicle vehicleElement : storage) {
            if(vehicleElement.getLicensePlate().equalsIgnoreCase(vehicle.getLicensePlate()) && vehicleElement.getCustomer().getId() == vehicle.getCustomer().getId()) {
                throw new IllegalArgumentException("Element is already present in the list...");
            }
        }
        storage.add(vehicle);
        return vehicle;
    }

    @Override
    public boolean remove(String licensePlate, int customerId) {
        Optional<Vehicle> vehicleOptional = find(licensePlate, customerId);
        if(vehicleOptional.isPresent()) {
            storage.remove(vehicleOptional.get());
            return true;
        }
        return false;
    }

    @Override
    public void update(Vehicle vehicle) {
        validateVehicle(vehicle);
        boolean isUpdated = false;
        for (Vehicle eachVehicle : storage) {
            if (eachVehicle.getLicensePlate().equalsIgnoreCase(vehicle.getLicensePlate())
                    && eachVehicle.getCustomer().getId() == vehicle.getCustomer().getId()) {
                eachVehicle.setType(vehicle.getType());
                isUpdated = true;
                System.out.println(storage);
                break;
            }
        }
        if(!isUpdated)
            throw new IllegalArgumentException("Element not found... Enter correct licence plate and customer id to update...");
    }

    @Override
    public Collection<Vehicle> findByCustomerId(int customerId) {
        validateCustomerId(customerId);
        Collection<Vehicle> vehicleList = new ArrayList<>();
        for (Vehicle vehicle : storage) {
            if(vehicle.getCustomer().getId() == customerId) {
                vehicleList.add(vehicle);
            }
        }
        return vehicleList;
    }

    private void validateVehicle(Vehicle vehicle) {
        if(vehicle == null)
            throw new NullPointerException("Vehicle cannot be null...");
        if(vehicle.getCustomer() == null)
            throw new NullPointerException("Customer cannot be null...");
    }

    private void validateCustomerId(int customerId) {
        if(customerId <= 0)
            throw new IllegalArgumentException("Customer Id cannot be zero or negative number...");
    }

    private void validateLicensePlate(String licensePlate) {
        if (licensePlate == null)
            throw new IllegalArgumentException("License plate cannot be null...");
    }
}
