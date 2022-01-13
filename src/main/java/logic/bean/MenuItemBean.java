package logic.bean;

import java.util.ArrayList;

public class MenuItemBean {
    private String name;
    private String description;
    private double price;
    private String category;
    private ArrayList<String> tags;
    private boolean availability;

    public MenuItemBean(String name, String description, double price, ArrayList<String> tags, boolean availability, String category) {
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.price = price;
        this.availability = availability;
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return this.category;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getTag(int i) {
        return tags.get(i);
    }

    public int getTagCount() {
        return tags.size();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public void clearTags() {
        this.tags.clear();
    }
}
