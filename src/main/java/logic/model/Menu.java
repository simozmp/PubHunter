package logic.model;

import java.util.ArrayList;

public class Menu {
    private ArrayList<MenuItem> items;
    private Restaurant restaurant;

    public Menu(Restaurant restaurant, ArrayList<MenuItem> items) {
        this(restaurant);
        this.items = items;
    }

    public Menu(Restaurant restaurant) {
        this.restaurant = restaurant;
        this.items = new ArrayList<>();
    }

    public MenuItem getItem(int index) {
        return items.get(index);
    }

    public int getSize() {
        return items.size();
    }

    public void addItem(MenuItem item) {
        this.items.add(item);
    }
}
