package logic.model;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private ArrayList<MenuItem> items;

    public Menu(List<MenuItem> items) {
    }

    public Menu() {
        this.items = new ArrayList<>();
    }

    public MenuItem getItem(int index) {
        return items.get(index);
    }

    public int getSize() {
        return items.size();
    }

    public void addItem(MenuItem item) {
        this.items.add(item);
    }
}
