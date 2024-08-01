package se.lexicon;

import se.lexicon.dao.*;
import se.lexicon.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

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
//        customerDao.update(new Customer(12, null, "133327896"));

        ParkingSpot parkingSpot1 = new ParkingSpot(1, false, 1000);
        Vehicle vehicleObj1 = new Vehicle("vehicle31", VehicleType.MOTORCYCLE, customer3);
        Reservation reservation1 = new Reservation(2, vehicleObj1, parkingSpot1);
        reservation1.create();
        customerDao.update(new Customer(customer3.getId(), null, null, reservation1));// Update customerDao details

        reservation1.extendTime(2);
        customerDao.update(new Customer(customer3.getId(), "Customer3", "133327895", reservation1));

        ParkingSpot parkingSpot2 = new ParkingSpot(2, false, 1000);
        Vehicle vehicleObj2 = new Vehicle(customer3, "vehicle32");
        Reservation reservation2 = new Reservation(2, vehicleObj2, parkingSpot2);
        reservation2.create();
        customerDao.update(new Customer(customer3.getId(), null, null, reservation2));// Update customerDao details

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

        System.out.println("==========================FIND ALL=============================");

        System.out.println(vehicleDao.findAll());

        System.out.println("===========================UPDATE==============================");

//        vehicleDao.update(new Vehicle("efi123", VehicleType.MOTORCYCLE));
        vehicleDao.update(new Vehicle(customer2,"rtg322"));
        vehicleDao.update(new Vehicle("efi123", VehicleType.OTHER, customer1));
//        vehicleDao.update(new Vehicle("efo123", VehicleType.OTHER, customer1));

        System.out.println();
        System.out.println("***************************************************************");
        System.out.println("***************************************************************");
        System.out.println();

        System.out.println("==========================RESERVATION==========================");
        System.out.println("===========================CREATE==============================");

        ReservationDao reservationDao = new ReservationDaoImpl();
        Reservation reservation3 = new Reservation("54694d54-dfc8-4c8f-9175-f6c054529ba8", LocalDateTime.of(LocalDate.of(2024,8,4), LocalTime.of(12, 0)),
                LocalDateTime.of(LocalDate.of(2024,8,4), LocalTime.of(14, 0)), vehicleObj1, parkingSpot2);
        System.out.println(reservationDao.create(reservation1));
        System.out.println(reservationDao.create(reservation2));
        System.out.println(reservationDao.create(reservation3));
//        reservationDao.create(reservation1);

        System.out.println("============================FIND===============================");

        Optional<Reservation> optionalReservation = reservationDao.find(reservation1.getId());
        optionalReservation.ifPresent(reservation -> System.out.println("Found Reservation: " + reservation));
        optionalReservation = reservationDao.find("qwerty");
        optionalReservation.ifPresentOrElse(
                (reservation) -> System.out.println("Found Reservation: " + reservation),
                () -> System.out.println("Reservation not found..."));

        System.out.println("==========================FIND ALL=============================");

        System.out.println(reservationDao.findAll());

        System.out.println("====================FIND BY LICENSE PLATE======================");

        System.out.println(reservationDao.findByLicensePlate("vehicle32"));
        System.out.println(reservationDao.findByLicensePlate("car12"));

        System.out.println("===========================UPDATE==============================");

        Reservation reservation4 = new Reservation("54694d54-dfc8-4c8f-9175-f6c054529ba8", LocalDateTime.of(LocalDate.of(2024,8,5), LocalTime.of(12, 0)),
                LocalDateTime.of(LocalDate.of(2024,8,5), LocalTime.of(14, 0)), new Vehicle(customer3, "vehicle31"), new ParkingSpot(3));
        reservationDao.update(reservation4);
//        reservationDao.update(new Reservation("54694d54", null, null, null, new ParkingSpot(1)));

        System.out.println("===========================REMOVE==============================");

        System.out.println("Is removed: " + reservationDao.remove(reservation1.getId()));
        System.out.println("Is removed: " + reservationDao.remove(reservation1.getId()));

        System.out.println();
        System.out.println("***************************************************************");
        System.out.println("***************************************************************");
        System.out.println();

        System.out.println("=========================PARKING SPOT==========================");
        System.out.println("===========================CREATE==============================");

        ParkingSpotDao parkingSpotDao = new ParkingSpotDaoImpl();
        ParkingSpot parkingSpot3 = new ParkingSpot(1, false, 1001);
        ParkingSpot parkingSpot4 = new ParkingSpot(2, false, 1001);
        ParkingSpot parkingSpot5 = new ParkingSpot(1, false, 1002);
        ParkingSpot parkingSpot6 = new ParkingSpot(2, false, 1002);
        parkingSpotDao.create(parkingSpot3);
        parkingSpotDao.create(parkingSpot4);
        parkingSpotDao.create(parkingSpot5);
        parkingSpotDao.create(parkingSpot6);
//        parkingSpotDao.create(parkingSpot6);

        System.out.println("==========================FIND ALL=============================");

        System.out.println(parkingSpotDao.findAll());

        System.out.println("======================FIND BY AREA CODE========================");

        System.out.println(parkingSpotDao.findByAreaCode(1001));

        System.out.println("===========================OCCUPY==============================");

        System.out.println("Before occupy: " + parkingSpotDao.find(1,1001));
        parkingSpotDao.occupyParkingSpot(1, 1001);
        System.out.println("After occupy: " + parkingSpotDao.find(1,1001));

        System.out.println("===========================VACATE==============================");

        System.out.println("Before vacate: " + parkingSpotDao.find(1,1001));
        parkingSpotDao.vacateParkingSpot(1,1001);
        System.out.println("After vacate: " + parkingSpotDao.find(1,1001));

        System.out.println("===========================REMOVE==============================");

        System.out.println("Is removed: " + parkingSpotDao.remove(2, 1001));
        System.out.println("Is removed: " + parkingSpotDao.remove(2, 1001));

        System.out.println();
        System.out.println("***************************************************************");
        System.out.println("***************************************************************");
    }
}