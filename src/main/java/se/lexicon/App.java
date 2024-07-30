package se.lexicon;

import se.lexicon.dao.VehicleDao;
import se.lexicon.dao.VehicleDaoImpl;
import se.lexicon.model.*;

import java.util.Collection;

public class App {
    public static void main(String[] args) {
        System.out.println("===========================CREATE==============================");
        Customer customer1 = new Customer(1, "Customer1", "123456789");
        Customer customer2 = new Customer(2, "Customer2", "133323455");

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
        vehicle1 = new Vehicle("efi123", VehicleType.OTHER, customer1);
        vehicle2 = vehicleDao.create(vehicle1);
        System.out.println(vehicle2);

        System.out.println("===============================================================");
        System.out.println("===========================REMOVE==============================");

        System.out.println("Is Vehicle Removed: " + vehicleDao.remove("efg123", 2));
        System.out.println("Is Vehicle Removed: " + vehicleDao.remove("efg123", 1));
        System.out.println("Is Vehicle Removed: " + vehicleDao.remove("efg13", 1));

        System.out.println("===============================================================");
        System.out.println("============================FIND===============================");

        Collection<Vehicle> vehiclesList = vehicleDao.findByCustomerId(1);
        System.out.println("Vehicles list of customer 1 : " + vehiclesList);

        System.out.println("===============================================================");
        System.out.println("===========================UPDATE==============================");

        vehicleDao.update(new Vehicle("efi123", VehicleType.MOTORCYCLE));
        vehicleDao.update(new Vehicle(customer2,"efi123"));
        vehicleDao.update(new Vehicle("efi123", VehicleType.OTHER, customer1));
    }
}
