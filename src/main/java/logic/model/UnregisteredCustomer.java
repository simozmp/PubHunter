package logic.model;

public class UnregisteredCustomer implements Customer {
    String id;

    public UnregisteredCustomer() {
        this.id = java.time.LocalDate.now() + "@" + java.time.LocalTime.now().toString();
    }

    @Override
    public String getId() {
        return id;
    }
}
