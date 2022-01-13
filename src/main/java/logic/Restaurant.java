package logic;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private List<Table> tables;
    private Menu menu;

    public Restaurant() {
        this.tables = new ArrayList<>();
        this.menu = new Menu(new ArrayList<>());
    }

    public Restaurant(Menu menu) {
        this.tables = new ArrayList<>();
        this.menu = menu;
    }

    public MenuItem getMenuItem(int index) {
        return menu.getItem(index);
    }

    public int getMenuSize() {
        return menu.getSize();
    }

    public void addTable(Table restaurantTable) {
        tables.add(restaurantTable);
    }
}
