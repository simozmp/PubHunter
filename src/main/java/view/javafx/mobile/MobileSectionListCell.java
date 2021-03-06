package view.javafx.mobile;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MobileSectionListCell extends javafx.scene.control.ListCell<String> {

    @FXML private AnchorPane root;
    @FXML private Label sectionLabel;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(String section, boolean empty) {

        super.updateItem(section, empty);

        if(section != null && !empty) {

            if (mLLoader == null) {
                URL url = Objects.requireNonNull(getClass().getResource("mobile-menu-section-list-cell.fxml"));
                mLLoader = new FXMLLoader(url);
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    Logger.getLogger("JavaFXApplication").log(Level.WARNING, "Couldn''t find fxml view file.", e);
                }
            }

            this.sectionLabel.setText(section);

            root.getStyleClass().clear();

            this.setGraphic(root);

            this.setFocusTraversable(false);
        } else
            this.setGraphic(null);
    }

    public String getCategory() {
        return sectionLabel.getText();
    }

}
