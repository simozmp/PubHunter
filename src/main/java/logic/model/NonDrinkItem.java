package logic.model;

public class NonDrinkItem extends MenuItem {

    private boolean availability;
    private String ingredientsString;

    public NonDrinkItem(String name, String category, String description, String ingredientsString, double price,
                        boolean availability) {
        super(name, category, description, price, -1);
        this.ingredientsString = ingredientsString;
        this.availability = availability;
    }

    public NonDrinkItem(String name, String category, String description, String ingredientsString, double price,
                        boolean availability, int persistenceId) {
        super(name, category, description, price, persistenceId);
        this.ingredientsString = ingredientsString;
        this.availability = availability;
    }

    @Override
    public boolean isAvailable() {
        return this.availability;
    }

    @Override
    public String getIngredientsString() {
        return this.ingredientsString;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public void setIngredientsString(String ingredientsString) {
        this.ingredientsString = ingredientsString;
    }
}
