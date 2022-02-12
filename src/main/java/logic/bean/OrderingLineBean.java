package logic.bean;

import logic.model.OrderingLine;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Bean class for OrderingLine class.
 */
public class OrderingLineBean implements PropertyChangeListener {

    private final String itemName;
    private final String itemDescription;
    private final double itemPrice;
    private int quantity;
    private String notes;

    public OrderingLineBean(OrderingLine line) {
        line.addPropertyChangeListener(this);
        this.itemName = line.getItemName();
        this.itemDescription = line.getItemDescription();
        this.itemPrice = line.getItemPrice();
        this.quantity = line.getQuantity();
        this.notes = line.getNotes();
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("quantity"))
            this.quantity = (int) evt.getNewValue();
    }

    public String getDescription() {
        return this.itemDescription;
    }

    public double getPrice() {
        return this.itemPrice;
    }
}
