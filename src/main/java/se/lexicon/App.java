package se.lexicon;

import se.lexicon.dao.CustomerDao;
import se.lexicon.dao.CustomerDaoImpl;
import se.lexicon.dao.VehicleDao;
import se.lexicon.dao.VehicleDaoImpl;
import se.lexicon.model.*;

import java.util.ArrayList;
import java.util.Collection;

public class App {
    public static void main(String[] args) {
        System.out.println("==========================CUSTOMER=============================");
        System.out.println("===========================CREATE==============================");

        Customer customer1 = new Customer("Customer1", "123456789");
        Customer customer2 = new Customer("Customer2", "133323455");
        Customer customer3 = new Customer("Customer3", "133327895");
        CustomerDao customerDao = new CustomerDaoImpl();
        System.out.println(customerDao.create(customer1));
        System.out.println(customerDao.create(customer2));
        System.out.println(customerDao.create(customer3));
//        customerDao.create(customer3);

        System.out.println("===========================REMOVE==============================");

        System.out.println("Is Customer " + customer2.getId() + " removed: " + customerDao.remove(customer2.getId()));
        System.out.println("Is Customer " + customer2.getId() + " removed: " + customerDao.remove(customer2.getId()));

        System.out.println("==========================FIND ALL=============================");

        ArrayList<Customer> allCustomers = (ArrayList<Customer>) customerDao.findAll();
        System.out.println(allCustomers);

        System.out.println("===========================UPDATE==============================");

        customerDao.update(new Customer(customer3.getId(), "Customer3 Customer3", null)); // Update customer name
        customerDao.update(new Customer(customer3.getId(), null, "133327896")); // Update phone number

        ParkingSpot parkingSpot1 = new ParkingSpot(1, false, 1000);
        Vehicle vehicleObj = new Vehicle("vehicle31", VehicleType.MOTORCYCLE, customer3);
        Reservation reservation1 = new Reservation(2, vehicleObj, parkingSpot1);
        reservation1.create();
        customerDao.update(new Customer(customer3.getId(), null, null, reservation1));// Update reservation details

        reservation1.extendTime(2);
        customerDao.update(new Customer(customer3.getId(), "Customer3", "133327895", reservation1));

        System.out.println("====================FIND BY RESERVATION ID=====================");

        System.out.println(customerDao.findByReservationId(customer3.getReservation().getId()));

        System.out.println();
        System.out.println("***************************************************************");
        System.out.println("***************************************************************");
        System.out.println();

        System.out.println("============================VEHICLE============================");
        System.out.println("===========================CREATE==============================");

        VehicleDao vehicleDao = new VehicleDaoImpl();
        Vehicle vehicle1 = new Vehicle("abc123", VehicleType.ELECTRIC, customer1);
        Vehicle vehicle2 = vehicleDao.create(vehicle1);
        System.out.println(vehicle2);
        vehicle1 = new Vehicle("efg123", VehicleType.CAR, customer1);
        vehicle2 = vehicleDao.create(vehicle1);
        System.out.println(vehicle2);
        vehicle1 = new Vehicle("rtg322", VehicleType.MOTORCYCLE, customer2);
        vehicle2 = vehicleDao.create(vehicle1);
        System.out.println(vehicle2);
        vehicle1 = new Vehicle(customer1,"efi123");
        vehicle2 = vehicleDao.create(vehicle1);
        System.out.println(vehicle2);

        System.out.println("===========================REMOVE==============================");

        System.out.println("Is Vehicle Removed: " + vehicleDao.remove("efg123", customer2.getId()));
        System.out.println("Is Vehicle Removed: " + vehicleDao.remove("efg123", customer1.getId()));
        System.out.println("Is Vehicle Removed: " + vehicleDao.remove("efg13", customer1.getId()));

        System.out.println("============================FIND===============================");

        Collection<Vehicle> vehiclesList = vehicleDao.findByCustomerId(customer1.getId());
        System.out.println("Vehicles list of customer 1 : " + vehiclesList);

        System.out.println("===========================UPDATE==============================");

//        vehicleDao.update(new Vehicle("efi123", VehicleType.MOTORCYCLE));
        vehicleDao.update(new Vehicle(customer2,"rtg322"));
        vehicleDao.update(new Vehicle("efi123", VehicleType.OTHER, customer1));
//        vehicleDao.update(new Vehicle("efo123", VehicleType.OTHER, customer1));
    }
}