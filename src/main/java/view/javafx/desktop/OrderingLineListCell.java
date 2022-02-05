package view.javafx.desktop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import logic.bean.MenuItemBean;
import logic.bean.OrderingLineBean;

import java.io.IOException;
import java.text.DecimalFormat;

public class OrderingLineListCell extends ListCell<OrderingLineBean> {

    private String notesForItem;
    private int count;

    OrderingLineBean bean;

    private FXMLLoader fxmlLoader;
    private DesktopOrderViewController viewController;
    private boolean extended;

    @FXML AnchorPane root;
    @FXML HBox editItemHBox;
    @FXML Button editButton;
    @FXML Button removeButton;
    @FXML Label countLabel;
    @FXML Label nameLabel;
    @FXML Label descriptionLabel;
    @FXML Label priceLabel;
    @FXML ImageView picImageView;
    @FXML TextArea notesTextArea;

    public OrderingLineListCell(DesktopOrderViewController viewController) {
        this.viewController = viewController;
    }

    @Override
    protected void updateItem(OrderingLineBean bean, boolean empty) {
        super.updateItem(bean, empty);

        if(bean != null && !empty) {

            this.bean = bean;

            if(fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("ordering-item-list-cell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            this.countLabel.setText(bean.getQuantity() + "x");

            ImageView trashIconImageView = new ImageView(new Image(
                    getClass().getResource("trash-icon.png").toExternalForm()));
            trashIconImageView.setPreserveRatio(true);
            trashIconImageView.setFitHeight(30);
            trashIconImageView.setFitWidth(30);
            removeButton.setGraphic(trashIconImageView);
            removeButton.setOnMouseClicked(mouseEvent -> onRemoveButtonClick());

            ImageView pencilIconImageView = new ImageView(new Image(
                    getClass().getResource("add-notes-icon.png").toExternalForm()));
            pencilIconImageView.setPreserveRatio(true);
            pencilIconImageView.setFitHeight(30);
            pencilIconImageView.setFitWidth(30);
            editButton.setGraphic(pencilIconImageView);
            editButton.setOnMouseClicked(mouseEvent -> onEditButtonMouseClick());

            this.picImageView.setImage(new Image(getClass().getResource("dish.png").toExternalForm()));
            this.nameLabel.setText(bean.getItemName());
            this.descriptionLabel.setText(bean.getDescription());
            this.priceLabel.setText("€ " + new DecimalFormat("#.00#").format(bean.getPrice()));

            notesTextArea.setPromptText("Write your notes here...");
            notesTextArea.setOnKeyTyped(keyEvent -> notesForItem = notesTextArea.getText());

            this.setGraphic(root);

            this.setFocusTraversable(false);
        } else
            setGraphic(null);
    }

    private void onRemoveButtonClick() {
        viewController.removeOrdering(bean);
    }

    @FXML
    private void onEditButtonMouseClick() {
        if(!extended)
            extendCell();
        else
            shrinkCell();
    }

    private void extendCell() {
        this.setPrefHeight(250);
        editItemHBox.setVisible(true);
        extended = true;
        updateItem(bean, false);
    }

    private void shrinkCell() {
        this.setPrefHeight(100);
        editItemHBox.setVisible(false);
        extended = false;
        updateItem(bean, false);
    }
}
