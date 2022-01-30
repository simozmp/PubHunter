package logic.model;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private User manager;
    private List<Table> tables;
    private List<Ingredient> supplies;
    private Menu menu;
    private int recordId;

    public Restaurant(String name, User manager) {
        this.name = name;
        this.manager = manager;
        this.menu = new Menu(this);
        this.tables = new ArrayList<>();
        this.supplies = new ArrayList<>();
        this.recordId = -1;
    }

    public User getManager() {
        return this.manager;
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

    public void setRecordId(int id) {
        this.recordId = id;
    }
}
