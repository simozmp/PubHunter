package logic.bean;

import logic.exception.LogicException;
import logic.model.OrderingLine;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class OrderingLineBean implements PropertyChangeListener {
    private OrderingLine reference;

    private String itemName;
    private String itemDescription;
    private double itemPrice;
    private int quantity;
    private String notes;

    public OrderingLineBean(OrderingLine line) {
        this.reference = line;
        this.reference.addPropertyChangeListener(this);
        this.itemName = line.getItemName();
        this.itemDescription = line.getItemDescription();
        this.itemPrice = line.getItemPrice();
        this.quantity = line.getQuantity();
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "quantity" -> { this.quantity = reference.getQuantity(); }
            case "item" -> {
                this.itemName = reference.getItemName();
                this.itemDescription = reference.getItemDescription();
                this.itemPrice = reference.getItemPrice();
            }
            default -> {
            }
        }
    }

    public String getDescription() {
        return this.itemDescription;
    }

    public double getPrice() {
        return this.itemPrice;
    }
}
