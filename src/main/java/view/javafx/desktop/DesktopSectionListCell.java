package view.javafx.desktop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DesktopSectionListCell extends javafx.scene.control.ListCell<String> {
    @FXML private BorderPane root;
    @FXML private Label sectionLabel;
    @FXML private Rectangle backgroundRectangle;
    @FXML private Rectangle accentRectangle;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(String section, boolean empty) {

        super.updateItem(section, empty);

        if(section != null && !empty) {

            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("menu-section-list-cell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    Logger.getLogger("JavaFXApplication").log(Level.WARNING, "Couldn''t find fxml view file.", e);
                }
            }

            if(this.isSelected()) {
                backgroundRectangle.getStyleClass().clear();
                backgroundRectangle.getStyleClass().add("menu-section-list-cell-selected");

                accentRectangle.getStyleClass().clear();
                accentRectangle.getStyleClass().add("section-accent-fill-selected");
            } else {
                backgroundRectangle.getStyleClass().clear();
                backgroundRectangle.getStyleClass().add("menu-section-list-cell");

                accentRectangle.getStyleClass().clear();
                accentRectangle.getStyleClass().add("section-accent-fill");
            }

            this.sectionLabel.setText(section);

            this.setGraphic(root);

            this.setFocusTraversable(false);
        } else
            this.setGraphic(null);
    }

    public String getCategory() {
        return sectionLabel.getText();
    }

}
