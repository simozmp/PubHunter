package view.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;

public class NoSelectionModel<T> extends MultipleSelectionModel<T> {
    @Override
    public ObservableList<Integer> getSelectedIndices() {
        //  Empty method to implement a "no selection" model
        return FXCollections.emptyObservableList();
    }

    @Override
    public ObservableList<T> getSelectedItems() {
        //  Empty method to implement a "no selection" model
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
    public void clearAndSelect(int i) {
        //  Empty method to implement a "no selection" model
    }

    @Override
    public void select(int i) {
        //  Empty method to implement a "no selection" model
    }

    @Override
    public void select(T t) {
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
        //  Empty method to implement a "no selection" model
        return false;
    }

    @Override
    public boolean isEmpty() {
        //  Empty method to implement a "no selection" model
        return false;
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
    public void selectFirst() {
        //  Empty method to implement a "no selection" model
    }

    @Override
    public void selectLast() {
        //  Empty method to implement a "no selection" model
    }
}
