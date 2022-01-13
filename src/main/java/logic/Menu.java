package logic;

import java.util.ArrayList;

public class Menu {
    private ArrayList<MenuItem> items;

    public Menu(ArrayList<MenuItem> items) {
        this.items = items;
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
