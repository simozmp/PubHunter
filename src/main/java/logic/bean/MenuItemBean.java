package logic.bean;

import logic.model.DrinkItem;
import logic.model.MenuItem;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Bean class for MenuItem class.
 */
public class MenuItemBean implements PropertyChangeListener {

    private String name;
    private String description;
    private String ingredients;
    private double price;
    private String category;
    private final List<String> tags;
    private boolean available;
    private final int theCocktailDBId;
    private final Image photo;

    public MenuItemBean(MenuItem reference) {
        //  Registering as a listener for the MenuItem
        reference.addPropertyChangeListener(this);

        this.name = reference.getName();
        this.description = reference.getDescription();
        this.ingredients = reference.getIngredientsString();

        this.tags = new ArrayList<>();
        for(int i=0; i<reference.getTagCount(); i++)
            this.tags.add(reference.getTag(i));

        this.price = reference.getPrice();
        this.available = reference.isAvailable();
        this.category = reference.getCategory();
        this.photo = reference.getPhoto();

        if(reference instanceof DrinkItem drinkReference)
            this.theCocktailDBId = drinkReference.getTheCocktailDBId();
        else
            this.theCocktailDBId = -1;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return this.category;
    }

    public String getDescription() {
        return description;
    }

    public String getIngredientsString() {
        return this.ingredients;
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

    public boolean isAvailable() {
        return available;
    }

    public int getTheCocktailDBId() {
        return theCocktailDBId;
    }

    public Image getPhoto() {
        return photo;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch(evt.getPropertyName()) {
            case "name":
                this.name = (String) evt.getNewValue();
                break;
            case "category":
                this.category = (String) evt.getNewValue();
                break;
            case "description":
                this.description = (String) evt.getNewValue();
                break;
            case "price":
                this.price = (double) evt.getNewValue();
                break;
            case "tags":
                if(evt.getOldValue() == null)
                    this.tags.add((String) evt.getNewValue());
                else if(evt.getNewValue() == null) {
                    String oldValue = (String) evt.getOldValue();
                    this.tags.remove(oldValue);
                }
                break;
            case "available":
                this.available = (boolean) evt.getNewValue();
                break;
            case "ingredients":
                this.ingredients = (String) evt.getNewValue();
                break;
            default:
                break;
        }
    }
}
