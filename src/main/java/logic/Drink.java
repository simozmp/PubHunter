package logic;

import java.util.ArrayList;

public class Drink extends MenuItem {

    public Drink(String name, String category, String description, double price, boolean availability, ArrayList<String> tags) {
        super(name, category, description, price, availability, tags);
    }
}
