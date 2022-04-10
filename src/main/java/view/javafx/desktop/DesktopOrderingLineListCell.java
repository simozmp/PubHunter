package view.javafx.desktop;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import logic.bean.OrderingLineBean;
import view.javafx.JFXMLApplication;
import view.javafx.OrderingLineListCell;

import java.text.DecimalFormat;
import java.util.Objects;

public class DesktopOrderingLineListCell extends OrderingLineListCell {


    @FXML private AnchorPane root;
    @FXML private HBox editItemHBox;
    @FXML private Button editButton;
    @FXML private Button removeButton;
    @FXML private Label countLabel;
    @FXML private Label nameLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label priceLabel;
    @FXML private ImageView picImageView;
    @FXML private TextArea notesTextArea;

    public DesktopOrderingLineListCell(DesktopOrderViewController viewController) {
        this.viewController = viewController;
        this.shrunkHeight = 100;
        this.extendedHeight = 250;
    }

    @Override
    protected void updateItem(OrderingLineBean bean, boolean empty) {
        super.updateItem(bean, empty);

        if(bean != null && !empty) {

            this.bean = bean;

            this.loadFxml("ordering-item-list-cell.fxml");

            this.countLabel.setText(bean.getQuantity() + "x");

            ImageView trashIconImageView = new ImageView(new Image(
                    Objects.requireNonNull(getClass().getResource("trash-icon.png")).toExternalForm()));
            trashIconImageView.setPreserveRatio(true);
            trashIconImageView.setFitHeight(30);
            trashIconImageView.setFitWidth(30);
            removeButton.setGraphic(trashIconImageView);
            removeButton.setOnMouseClicked(mouseEvent -> onRemoveButtonClick());

            ImageView pencilIconImageView = new ImageView(new Image(
                    Objects.requireNonNull(getClass().getResource("add-notes-icon.png")).toExternalForm()));
            pencilIconImageView.setPreserveRatio(true);
            pencilIconImageView.setFitHeight(30);
            pencilIconImageView.setFitWidth(30);
            editButton.setGraphic(pencilIconImageView);
            editButton.setOnMouseClicked(mouseEvent -> onEditButtonMouseClick(editItemHBox));

            this.picImageView.setImage(new Image(
                    Objects.requireNonNull(JFXMLApplication.class.getResource("dish.png")).toExternalForm()));
            this.nameLabel.setText(bean.getItemName());
            this.descriptionLabel.setText(bean.getDescription());
            this.priceLabel.setText("€ " + new DecimalFormat("#.00#").format(bean.getPrice()));

            notesTextArea.setPromptText("Write your notes here...");
            notesTextArea.setOnKeyTyped(keyEvent -> viewController.addNotesToLine(notesTextArea.getText(), bean));

            this.setGraphic(root);

            this.setFocusTraversable(false);
        } else
            setGraphic(null);
    }
}
