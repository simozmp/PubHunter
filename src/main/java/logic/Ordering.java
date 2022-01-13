package logic;

import java.util.ArrayList;

public class Ordering {
    private ArrayList<MenuItem> items;

    public Ordering() {

    }

    public void add(MenuItem item) {
        items.add(item);
    }

    public void clear() {
        items.clear();
    }
}
