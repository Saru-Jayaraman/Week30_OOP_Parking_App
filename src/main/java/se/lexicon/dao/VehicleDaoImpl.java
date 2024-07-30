package se.lexicon.dao;

import se.lexicon.model.Vehicle;

import java.util.*;

public class VehicleDaoImpl implements VehicleDao {

    private final List<Vehicle> storage = new ArrayList<>();

    @Override
    public Optional<Vehicle> find(String licensePlate, int customerId) {
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
            if(vehicleElement.getLicensePlate().equals(vehicle.getLicensePlate()) && vehicleElement.getCustomer().getId() == vehicle.getCustomer().getId()) {
                throw new IllegalArgumentException("Element is already present in the list...");
            }
        }
        storage.add(vehicle);
        return vehicle;
    }

    @Override
    public boolean remove(String licensePlate, int customerId) {
        validateLicensePlate(licensePlate);
        validateCustomerId(customerId);
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
            if(eachVehicle.getLicensePlate().equals(vehicle.getLicensePlate())) {
                if((vehicle.getCustomer() != null) && (eachVehicle.getCustomer() != vehicle.getCustomer()))
                    eachVehicle.setCustomer(vehicle.getCustomer());
                if((vehicle.getType()) != null && !(eachVehicle.getType().equals(vehicle.getType())))
                    eachVehicle.setType(vehicle.getType());
                isUpdated = true;
            }
        }
        if(!isUpdated) // Creating an entry in storage list if licensePlate is not found
            storage.add(vehicle);
        System.out.println("Updated List: " + storage);
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
