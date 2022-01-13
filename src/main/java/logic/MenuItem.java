package logic;

import logic.bean.MenuItemBean;

import java.util.ArrayList;

public class MenuItem {
    private String name;
    private String category;
    private String description;
    private double price;
    private boolean available;
    private ArrayList<String> tags;

    public MenuItem(String name, String category, String description, double price, boolean availability, ArrayList<String> tags) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.available = availability;
        this.tags = tags;
    }

    public MenuItemBean toBean() {
        return new MenuItemBean(name,
                description,
                price,
                tags,
                available,
                category);
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }


    public boolean isAvailable() {
        return available;
    }

    public ArrayList<String> getTags() {
        return tags;
    }
}
