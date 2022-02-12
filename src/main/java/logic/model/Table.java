package logic.model;

public class Table {
    private final String representation;
    private final int seats;
    private Restaurant restaurant;

    public Table(String representation, int seats) {
        this.representation = representation;
        this.seats = seats;
    }

    public void bindToRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getRepresentation() {
        return this.representation;
    }

    public int getSeats() {
        return this.seats;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public String getRestaurantName() {
        return restaurant.getName();
    }

    public int getRestaurantId() {
        return restaurant.getRecordId();
    }
}
