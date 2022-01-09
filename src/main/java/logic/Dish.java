package logic;

import java.util.List;

public class Dish {
    private String name;
    private String ingredients;
    private double price;
    private List<String> tags;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Dish(String name, String ingredients, double price, List<String> tags) {
        this.name = name;
        this.ingredients = ingredients;
        this.tags = tags;
        this.price = price;
    }

    public String getIngredients() {
        return ingredients;
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

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
