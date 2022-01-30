package logic.model;

import logic.exception.LogicException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class TableService implements Serializable {
    private Table tableReference;
    private LocalDate openedDate;
    private LocalTime openedTime;
    private LocalDate closedDate;
    private LocalTime closedTime;
    private ArrayList<Ordering> orderings;
    private Customer customer;
    private int recordId;

    public TableService(Table tableReference, Customer customer, LocalDate date, LocalTime openedTime) {
        this.tableReference = tableReference;
        this.customer = customer;
        this.openedDate = date;
        this.openedTime = openedTime;
        this.orderings = new ArrayList<>();
    }

    public void addOrdering(Ordering newOrdering) throws LogicException {
        newOrdering.setTableService(this);
        newOrdering.setSent();
        orderings.add(newOrdering);
    }

    public Ordering getOrdering(int index) {
        return this.orderings.get(index);
    }

    public int getOrderingsCount() {
        return this.orderings.size();
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public LocalDate getOpenedDate() {
        return openedDate;
    }

    public LocalTime getOpenedTime() {
        return openedTime;
    }

    public LocalDate getClosedDate() {
        return closedDate;
    }

    public LocalTime getClosedTime() {
        return closedTime;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public Table getTable() {
        return tableReference;
    }

    public String getTableRepresentation() {
        return tableReference.getRepresentation();
    }

    public int getRestaurantId() {
        return tableReference.getRestaurantId();
    }

    public String getRestaurantName() {
        return this.tableReference.getRestaurantName();
    }
}
