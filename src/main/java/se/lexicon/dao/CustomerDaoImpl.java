package se.lexicon.dao;

import se.lexicon.dao.sequencer.CustomerIdSequencer;
import se.lexicon.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDaoImpl implements CustomerDao {

    private final List<Customer> storage = new ArrayList<>();

    @Override
    public Customer create(Customer customer) {
        validateCustomer(customer);
        System.out.println(customer);
        if(customer.getId() != 0) // default value set for Customer id is 0 when creating customer object using the constructor Customer(name, phoneNumber)
            // to avoid duplicate entry in storage area
            throw new IllegalArgumentException("Customer is already present...");
        else {
            customer.setId(CustomerIdSequencer.nextId());
            storage.add(customer);
            System.out.println("Create list: " + storage);
            return customer;
        }
    }

    @Override
    public Optional<Customer> find(int id) {
        validateCustomerId(id);
        for (Customer customerElement : storage) {
            if(customerElement.getId() == id) {
                return Optional.of(customerElement);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean remove(int id) {
        validateCustomerId(id);
        Optional<Customer> vehicleOptional = find(id);
        if(vehicleOptional.isPresent()) {
            storage.remove(vehicleOptional.get());
            return true;
        }
        return false;
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public void update(Customer customer) {
        validateCustomer(customer);
        for (Customer customerElement : storage) {
            if (customerElement.getId() == customer.getId()) {
                if(customer.getName() != null)
                    customerElement.setName(customer.getName());
                if(customer.getPhoneNumber() != null)
                    customerElement.setPhoneNumber(customer.getPhoneNumber());
                if(customer.getReservation() != null)
                    customerElement.setReservation(customer.getReservation());
                System.out.println(storage);
                break;
            }
        }
    }

    @Override
    public Customer findByReservationId(String reservationId) {
        validateReservationId(reservationId);
        for(Customer customer : storage) {
            if(customer.getReservation() != null) {
                if(customer.getReservation().getId().equalsIgnoreCase(reservationId)) {
                    return customer;
                }
            }
        }
        return null;
    }

    private void validateCustomer(Customer customer) {
        if(customer == null)
            throw new NullPointerException("Customer cannot be null...");
    }

    private void validateCustomerId(int customerId) {
        if(customerId <= 0)
            throw new IllegalArgumentException("Customer Id cannot be zero or negative number...");
    }

    private void validateReservationId(String reservationId) {
        if(reservationId == null)
            throw new NullPointerException("Reservation Id cannot be null...");
    }
}
