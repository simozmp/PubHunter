package view.javafx.mobile;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import logic.bean.OrderingLineBean;
import view.javafx.OrderingLineListCell;

import java.text.DecimalFormat;
import java.util.Objects;

public class MobileOrderingLineListCell extends OrderingLineListCell {

    @FXML private AnchorPane editItemAnchorPane;
    @FXML private AnchorPane root;
    @FXML private ImageView writingNotesStatusIconImageView;
    @FXML private Button editButton;
    @FXML private Button removeButton;
    @FXML private Label countLabel;
    @FXML private Label nameLabel;
    @FXML private Label priceLabel;
    @FXML private ImageView picImageView;
    @FXML private TextArea notesTextArea;

    public MobileOrderingLineListCell(MobileOrderViewController viewController) {
        this.viewController = viewController;
        this.extendedHeight = 170;
        this.shrunkHeight = 70;
    }

    @Override
    protected void updateItem(OrderingLineBean bean, boolean empty) {
        super.updateItem(bean, empty);

        if(bean != null && !empty) {

            this.bean = bean;

            this.loadFxml("mobile-ordering-line-list-cell.fxml");

            this.countLabel.setText(bean.getQuantity() + "x");

            ImageView trashIconImageView = new ImageView(new Image(
                    Objects.requireNonNull(getClass().getResource("octicon_trash-24.png")).toExternalForm()));
            removeButton.setGraphic(trashIconImageView);
            removeButton.setOnMouseClicked(mouseEvent -> onRemoveButtonClick());

            ImageView pencilIconImageView = new ImageView(new Image(
                    Objects.requireNonNull(getClass().getResource("clarity_note-edit-line.png")).toExternalForm()));
            editButton.setGraphic(pencilIconImageView);
            editButton.setOnMouseClicked(mouseEvent -> onEditButtonMouseClick(editItemAnchorPane));

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
}
