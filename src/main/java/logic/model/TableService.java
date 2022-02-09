package logic.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TableService {
    private final Table tableReference;
    private final LocalDate openedDate;
    private final LocalTime openedTime;
    private LocalDate closedDate;
    private LocalTime closedTime;
    private final List<Ordering> orderings;
    private final Customer customer;
    private int recordId;

    public TableService(Table tableReference, Customer customer, LocalDate date, LocalTime openedTime) {
        this.tableReference = tableReference;
        this.customer = customer;
        this.openedDate = date;
        this.openedTime = openedTime;
        this.orderings = new ArrayList<>();
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
        return closedDate == null ? LocalDate.MIN : closedDate;
    }

    public LocalTime getClosedTime() {
        return closedTime == null ? LocalTime.MIN : closedTime;
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
