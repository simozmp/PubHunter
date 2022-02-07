package logic.model;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class OrderingLine {
    private final PropertyChangeSupport propertyChangeSupport;
    private int quantity;
    private MenuItem item;
    private String notes;

    public OrderingLine(MenuItem item) {
        this(item, 1);
    }

    public OrderingLine(MenuItem item, int count) {
        this.propertyChangeSupport = new PropertyChangeSupport(this);
        this.item = item;
        this.quantity = count;
    }

    public int getQuantity() {
        return quantity;
    }

    public MenuItem getItem() {
        return item;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        String oldNotes = this.notes;

        this.notes = notes;
        propertyChangeSupport.firePropertyChange("notes", oldNotes, this.notes);
    }

    public void decreaseCount() {
        if(quantity > 0) {
            this.quantity--;
            propertyChangeSupport.firePropertyChange("count", this.quantity + 1, this.quantity);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void increaseCount() {
        this.quantity++;
        propertyChangeSupport.firePropertyChange("count", this.quantity - 1, this.quantity);
    }

    public String getItemName() {
        return item.getName();
    }

    public String getItemDescription() {
        return item.getDescription();
    }

    public double getItemPrice() {
        return item.getPrice();
    }

    public void reset() {
        this.quantity = 0;
        this.item = null;
        this.notes = "";
        propertyChangeSupport.firePropertyChange("reset", 0, 0);
    }

    public void addNotes(String text) {
        this.notes = text;
    }
}
