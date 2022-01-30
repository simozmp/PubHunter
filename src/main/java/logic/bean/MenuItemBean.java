package logic.bean;

import logic.model.MenuItem;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class MenuItemBean implements PropertyChangeListener {
    private MenuItem reference;
    private String name;
    private String description;
    private double price;
    private String category;
    private final List<String> tags;
    private boolean available;

    public MenuItemBean(String name, String description, double price, List<String> tags, boolean availability,
                        String category) {
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.price = price;
        this.available = availability;
        this.category = category;
    }

    public MenuItemBean(MenuItem reference) {

        this.reference = reference;

        //  Registering as a listener for the MenuItem
        reference.addPropertyChangeListener(this);

        //  Retrieving MenuItem data
        this.name = reference.getName();
        this.description = reference.getDescription();

        this.tags = new ArrayList<>();
        for(int i=0; i<reference.getTagCount(); i++)
            this.tags.add(reference.getTag(i));

        this.price = reference.getPrice();
        this.available = reference.isAvailable();
        this.category = reference.getCategory();
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

    public String getName() {
        return name;
    }

    public String getTag(int i) {
        return tags.get(i);
    }

    public int getTagCount() {
        return tags.size();
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return available;
    }

    public MenuItem getReference() {
        return this.reference;
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
                else if(evt.getNewValue() == null)
                    this.tags.remove(evt.getOldValue());
                break;
            case "available":
                this.available = (boolean) evt.getNewValue();
                break;
            default:
                break;
        }
    }


    /**
     * Unbinds instance from referenced model instance
     */
    public void unbindFromReference() {
        if(reference != null)
            reference.removePropertyChangeListener(this);
    }
}
