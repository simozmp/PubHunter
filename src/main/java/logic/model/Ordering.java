package logic.model;

import logic.bean.OrderingLineBean;
import logic.exception.LogicException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The model class for an ordering
 */
public class Ordering implements Serializable {

    /**
     * The TableService associated with this ordering
     */
    private TableService service;

    /**
     * The time taken at the sending of this ordering
     */
    private LocalTime sentTime;

    /**
     * The date taken at the sending of this ordering
     */
    private LocalDate sentDate;

    /**
     * The lines composing this ordering
     */
    private final List<OrderingLine> lines;
    private int recordId;

    /**
     * Constructor of the ordering
     *
     * @param service The TableService associated with this ordering
     */
    public Ordering(TableService service) {
        this.service = service;
        this.lines = new ArrayList<>();
        this.recordId = -1;
    }

    /**
     * Operation to add an item to the ordering
     *
     * @param item The item to add
     */
    public boolean add(MenuItem item) {

        boolean result = false;

        if(item.isAvailable()) {
            for (OrderingLine line : lines)
                if (Objects.equals(line.getItem().getName(), item.getName())) {
                    line.increaseCount();
                    return true;
                }

            lines.add(new OrderingLine(item));
            result = true;
        }

        return result;
    }

    /**
     * Operation to clear the ordering
     */
    public void clear() {
        for(OrderingLine line : lines)
            line.reset();
        lines.clear();
    }

    /**
     * @return The number of lines in the ordering
     */
    public int getLinesCount() {
        return this.lines.size();
    }

    /*

     */
    public int getItemQuantity(MenuItem item) {
        int result = 0;

        for(OrderingLine line : lines)
            if(line.getItem() == item)
                result += line.getQuantity();

        return result;
    }

    /**
     * Getter for the index-th line
     *
     * @param index Index of the line
     * @return The line requested
     */
    public OrderingLine getLine(int index) {
        return this.lines.get(index);
    }

    /**
     *
     * @return The persistence id of the TableService associated with the Ordering
     */
    public int getServiceId() {
        return this.service.getRecordId();
    }

    /**
     * Operation to remove an item from the ordering
     *
     * @param toRemove The item to remove from the ordering
     * @throws LogicException If the item is not in the current ordering
     */
    public boolean remove(MenuItem toRemove) throws LogicException {

        boolean found = false;

        for(OrderingLine entry : lines)
            if(entry.getItem() == toRemove) {
                if (entry.getQuantity() > 1)
                    entry.decreaseCount();
                else
                    lines.remove(entry);

                found = true;
                break;
            }

        if(!found)
            throw new LogicException("Couldn't remove item for it is not in the ordering!");

        return true;
    }

    /**
     * Getter for sentTime
     *
     * @return The time taken at ordering sending
     */
    public LocalTime getSentTime() {
        return sentTime;
    }

    /**
     * Getter for sentDate
     *
     * @return The date taken at ordering sending
     */
    public LocalDate getSentDate() {
        return sentDate;
    }

    /**
     * Setter for tableService
     *
     * @throws LogicException If ordering has already been bind
     */
    public void setTableService(TableService tableService) throws LogicException {
        if (this.service == null)
            this.service = tableService;
        else
            throw new LogicException("Client is trying to bind an already bind ordering.");
    }

    /**
     * Operation to set the Ordering as sent. This marks the ordering as sent at LocalTime.now(), LocalDate.now()
     */
    public void setSent() {
        this.sentDate = LocalDate.now();
        this.sentTime = LocalTime.now();
    }

    /**
     * Getter for persistence id
     *
     * @return The record id in persistence, or -1 if the record is not tracked by the instance
     */
    public int getRecordId() {
        return this.recordId;
    }

    /**
     * Setter for persistence id
     *
     * @param recordId The record id in persistence.
     */
    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public boolean removeLine(OrderingLineBean lineBean) throws LogicException {
        boolean found = false;

        for(OrderingLine line : lines)
            if(line.getItem().getName() == lineBean.getItemName()) {
                found = true;
                lines.remove(line);
                break;
            }

        if(!found)
            throw new LogicException("Error while removing line from ordering: unable to find line relative to bean");

        return true;
    }

    public void addNotesToLine(String text, int i) {
        lines.get(i).addNotes(text);
    }
}
