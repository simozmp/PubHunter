package view.javafx.desktop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;
import logic.bean.MenuItemBean;

public class NoSelectionModel extends MultipleSelectionModel<MenuItemBean> {
    @Override
    public void clearAndSelect(int i) {
        //  Empty method to implement a "no selection" model
    }

    @Override
    public void select(int i) {
        //  Empty method to implement a "no selection" model
    }

    @Override
    public void select(MenuItemBean menuItemBean) {
        //  Empty method to implement a "no selection" model
    }

    @Override
    public void clearSelection(int i) {
        //  Empty method to implement a "no selection" model
    }

    @Override
    public void clearSelection() {
        //  Empty method to implement a "no selection" model
    }

    @Override
    public boolean isSelected(int i) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public void selectPrevious() {
        //  Empty method to implement a "no selection" model
    }

    @Override
    public void selectNext() {
        //  Empty method to implement a "no selection" model
    }

    @Override
    public ObservableList<Integer> getSelectedIndices() {
        return FXCollections.emptyObservableList();
    }

    @Override
    public ObservableList<MenuItemBean> getSelectedItems() {
        return FXCollections.emptyObservableList();
    }

    @Override
    public void selectIndices(int i, int... ints) {
        //  Empty method to implement a "no selection" model
    }

    @Override
    public void selectAll() {
        //  Empty method to implement a "no selection" model
    }

    @Override
    public void selectFirst() {
        //  Empty method to implement a "no selection" model
    }

    @Override
    public void selectLast() {
        //  Empty method to implement a "no selection" model
    }
}
