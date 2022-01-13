package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;

public class SectionListCellController extends javafx.scene.control.ListCell<String> {
    @FXML private BorderPane root;
    @FXML private Text sectionName;
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
                    e.printStackTrace();
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

            this.sectionName.setText(section);

            this.setGraphic(root);

            this.setFocusTraversable(false);
        } else
            this.setGraphic(null);
    }

    public String getCategory() {
        return sectionName.getText();
    }

}