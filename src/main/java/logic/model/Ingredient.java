package logic.model;

import java.io.Serializable;
import java.util.Locale;

public class Ingredient implements Serializable {
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
