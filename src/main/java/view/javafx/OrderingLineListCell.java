package view.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import logic.bean.OrderingLineBean;
import view.OrderViewController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class OrderingLineListCell extends ListCell<OrderingLineBean> {

    protected FXMLLoader fxmlLoader;

    protected OrderingLineBean bean;
    protected OrderViewController viewController;
    protected boolean extended;

    protected int shrunkHeight;
    protected int extendedHeight;

    protected void onRemoveButtonClick() {
        viewController.onRemoveOrderingLineButton(bean);
    }

    @FXML
    protected void onEditButtonMouseClick(Node editItemNode) {
        if(!extended)
            extendCell(editItemNode);
        else
            shrinkCell(editItemNode);
    }

    protected void extendCell(Node editItemNode) {
        this.setPrefHeight(extendedHeight);
        editItemNode.setVisible(true);
        extended = true;
        updateItem(bean, false);
    }

    protected void shrinkCell(Node editItemNode) {
        this.setPrefHeight(shrunkHeight);
        editItemNode.setVisible(false);
        extended = false;
        updateItem(bean, false);
    }

    protected void loadFxml(String filename) {
        if(fxmlLoader == null) {
            fxmlLoader = new FXMLLoader(getClass().getResource(filename));
            fxmlLoader.setController(this);

            try {
                fxmlLoader.load();
            } catch (IOException e) {
                Logger.getLogger("JavaFXApplication").log(Level.WARNING, "Couldn''t find fxml view file.", e);
            }
        }
    }
}
