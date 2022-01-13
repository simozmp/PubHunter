package logic;

public class Table {
    private String representation;
    private Restaurant restaurant;

    public Table(String representation, Restaurant restaurant) {
        this.representation = representation;
        this.restaurant = restaurant;

        restaurant.addTable(this); // IS THIS CORRECT? TODO: VERIFY
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public String getRepresentation() {
        return this.representation;
    }
}
