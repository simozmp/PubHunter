package logic.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {
    private Customer customer;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private Table table;
    //  The service associated to the booking. It should be null until a customer registers into the restaurant
    private TableService service;
    private Restaurant restaurant;

    /*
    Standard constructor, called when creating a new Booking
     */
    public Booking(Customer customer, Table table, LocalDate bookingDate, LocalTime bookingTime) {
        this.customer = customer;
        this.table = table;
        this.restaurant = table.getRestaurant();
    }

    /*
    DAO contructor, called when retrieving an existing booking from persistence
     */
    Booking(Customer customer, Table table, LocalDate bookingDate, LocalTime bookingTime, TableService bookingService) {
        this.customer = customer;
        this.table = table;
        this.service = bookingService;
    }

    public TableService getService() {
        return service;
    }

    public Restaurant getRestaurant() {
        return table.getRestaurant();
    }
}