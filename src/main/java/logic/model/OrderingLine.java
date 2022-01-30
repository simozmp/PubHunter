package logic.model;


public class OrderingLine {
    private int quantity;
    private Ordering ordering;
    private MenuItem item;
    private String notes;

    public OrderingLine(Ordering ordering, MenuItem item) {
        this(ordering, item, 1);
    }

    public OrderingLine(Ordering ordering, MenuItem item, int count) {
        this.ordering = ordering;
        this.item = item;
        this.quantity = count;
    }

    public int getQuantity() {
        return quantity;
    }

    public MenuItem getItem() {
        return item;
    }

    public void setItem(MenuItem item) {
        this.item = item;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void decreaseCount() {
        this.quantity--;
    }

    public void increaseCount() {
        this.quantity++;
    }
}
