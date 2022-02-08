package logic.model;

import java.util.Locale;

public class Ingredient {
    String description;

    public Ingredient(String description) {
        this.description = description.toLowerCase(Locale.ROOT);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
