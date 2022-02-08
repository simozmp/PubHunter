package logic.model;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private final String name;
    private final User manager;
    private final List<Table> tables;
    private final List<Ingredient> supplies;
    private final Menu menu;
    private int recordId;

    public Restaurant(String name, User manager, int recordId) {
        this.name = name;
        this.manager = manager;
        this.menu = new Menu();
        this.tables = new ArrayList<>();
        this.supplies = new ArrayList<>();
        this.recordId = recordId;
    }

    public Restaurant(String name, User manager) {
        this(name, manager, -1);
    }

    public String getManagerEmailString() {
        return this.manager.getEmailAddress().toString();
    }

    public String getName() {
        return this.name;
    }

    public MenuItem getMenuItem(int index) {
        return menu.getItem(index);
    }

    public int getMenuSize() {
        return menu.getSize();
    }

    public void addMenuItem(MenuItem item) {
        this.menu.addItem(item);
    }

    public void addSupply(Ingredient ingredient) {
        this.supplies.add(ingredient);
    }

    public int getSuppliesCount() {
        return this.supplies.size();
    }

    public Ingredient getSupply(int index) {
        return this.supplies.get(index);
    }

    public void addTable(Table restaurantTable) {
        restaurantTable.registerAtRestaurant(this);
        tables.add(restaurantTable);
    }

    public Table getTable(int index) {
        return tables.get(index);
    }

    public int getTablesCount() {
        return tables.size();
    }

    public int getRecordId() {
        return this.recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }
}
