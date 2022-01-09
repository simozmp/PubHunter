package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class SectionListViewCell extends javafx.scene.control.ListCell<String> {
    @FXML private BorderPane root;
    @FXML private Text sectionName;

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

            this.sectionName.setText(section);

            this.setGraphic(root);

            this.setFocusTraversable(false);
        } else
            this.setGraphic(null);
    }

}
