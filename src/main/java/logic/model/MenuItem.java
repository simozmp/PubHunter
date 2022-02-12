package logic.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Domain model class for an order-able product of a restaurant menu
 */
public abstract class MenuItem {

    /**
     * To dispatch PropertyChangeEvent-s to beans that implements PropertyChangeListener
     * and are bound to an instance of this class.
     */
    protected final PropertyChangeSupport propertyChangeSupport;

    /*
        Set of properties for a menu item (business model)
     */
    private String name;
    private String category;
    private String description;
    private double price;
    private final List<String> tags;
    private int persistenceId;

    protected MenuItem(String name, String category, String description, double price, int persistenceId) {
        this.propertyChangeSupport = new PropertyChangeSupport(this);
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.persistenceId = persistenceId;
        this.tags = new ArrayList<>();
    }

    protected MenuItem(String name, String category, String description, double price) {
        this(name, category, description, price, -1);
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public abstract boolean isAvailable();

    public abstract String getIngredientsString();

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

    public int getTagCount() {
        return tags.size();
    }

    public String getTag(int index) {
        return tags.get(index);
    }

    public void setName(String name) {
        String oldValue = this.name;
        this.name = name;
        propertyChangeSupport.firePropertyChange("name", oldValue, name);
    }

    public void setCategory(String category) {
        String oldValue = this.category;
        this.category = category;
        propertyChangeSupport.firePropertyChange("category", oldValue, category);
    }

    public void setDescription(String description) {
        String oldValue = this.description;
        this.description = description;
        propertyChangeSupport.firePropertyChange("description", oldValue, description);
    }

    public void setPrice(double price) {
        double oldValue = this.price;
        this.price = price;
        propertyChangeSupport.firePropertyChange("price", oldValue, price);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public int getRecordId() {
        return this.persistenceId;
    }

    public void setRecordId(int recordId) {
        this.persistenceId = recordId;
    }
}
