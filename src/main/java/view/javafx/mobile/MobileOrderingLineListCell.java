package view.javafx.mobile;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import logic.bean.OrderingLineBean;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;

public class MobileOrderingLineListCell extends javafx.scene.control.ListCell<logic.bean.OrderingLineBean> {

    OrderingLineBean bean;

    private FXMLLoader fxmlLoader;
    private final MobileOrderViewController viewController;
    private boolean extended;

    @FXML AnchorPane root;
    @FXML AnchorPane editItemAnchorPane;
    @FXML ImageView writingNotesStatusIconImageView;
    @FXML Button editButton;
    @FXML Button removeButton;
    @FXML Label countLabel;
    @FXML Label nameLabel;
    @FXML Label priceLabel;
    @FXML ImageView picImageView;
    @FXML TextArea notesTextArea;

    public MobileOrderingLineListCell(MobileOrderViewController viewController) {
        this.viewController = viewController;
    }

    @Override
    protected void updateItem(OrderingLineBean bean, boolean empty) {
        super.updateItem(bean, empty);

        if(bean != null && !empty) {

            this.bean = bean;

            if(fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("mobile-ordering-line-list-cell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            this.countLabel.setText(bean.getQuantity() + "x");

            ImageView trashIconImageView = new ImageView(new Image(
                    getClass().getResource("octicon_trash-24.png").toExternalForm()));
            removeButton.setGraphic(trashIconImageView);
            removeButton.setOnMouseClicked(mouseEvent -> onRemoveButtonClick());

            ImageView pencilIconImageView = new ImageView(new Image(
                    getClass().getResource("clarity_note-edit-line.png").toExternalForm()));
            editButton.setGraphic(pencilIconImageView);
            editButton.setOnMouseClicked(mouseEvent -> onEditButtonMouseClick());

            this.nameLabel.setText(bean.getItemName());
            this.priceLabel.setText("€ " + new DecimalFormat("#.00#").format(bean.getPrice()));

            Image writingNotesReadingIcon = new Image(Objects.requireNonNull(getClass().getResource("mdi_dots-circle.png")).toExternalForm());
            Image writingNotesDoneIcon = new Image(Objects.requireNonNull(getClass().getResource("charm_tick.png")).toExternalForm());

            notesTextArea.setPromptText("Write your notes here...");
            notesTextArea.setOnKeyTyped(keyEvent -> {
                writingNotesStatusIconImageView.setImage(writingNotesReadingIcon);
                viewController.addNotesToLine(notesTextArea.getText(), bean);
                writingNotesStatusIconImageView.setImage(writingNotesDoneIcon);
            });

            this.setGraphic(root);

            this.setFocusTraversable(false);
        } else
            setGraphic(null);
    }

    private void onRemoveButtonClick() {
        viewController.onRemoveOrderingLineButton(bean);
    }

    @FXML
    private void onEditButtonMouseClick() {
        if(!extended)
            extendCell();
        else
            shrinkCell();
    }

    private void extendCell() {
        this.setPrefHeight(170);
        editItemAnchorPane.setVisible(true);
        extended = true;
        updateItem(bean, false);
    }

    private void shrinkCell() {
        this.setPrefHeight(70);
        editItemAnchorPane.setVisible(false);
        extended = false;
        updateItem(bean, false);
    }
}
