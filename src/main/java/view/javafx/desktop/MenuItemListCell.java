package view.javafx.desktop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import logic.bean.MenuItemBean;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuItemListCell extends ListCell<MenuItemBean> {
    @FXML private StackPane root;
    @FXML private WebView infoWebView;
    @FXML private Label nameLabel;
    @FXML private Label ingredientsLabel;
    @FXML private Button tag1Btn;
    @FXML private Button tag2Btn;
    @FXML private Button tag3Btn;
    @FXML private Label priceLabel;
    @FXML private Button minusButton;
    @FXML private Label countLabel;
    @FXML private Button plusButton;
    @FXML private Text notAvailableLabel;

    private FXMLLoader fxmlLoader;
    private final DesktopOrderViewController viewController;

    private int count;

    public MenuItemListCell(DesktopOrderViewController viewController) {
        this.viewController = viewController;
    }

    @Override
    protected void updateItem(MenuItemBean bean, boolean empty) {

        super.updateItem(bean, empty);

        if(bean != null && !empty) {

            WebEngine infoWebEngine;

            count = viewController.getItemOrderingQuantity(bean);

            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("menu-item-list-cell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    Logger.getLogger("JavaFXApplication").log(Level.WARNING, "Couldn''t find fxml view file.", e);
                }
            }

            this.nameLabel.setText(bean.getName());
            this.ingredientsLabel.setText(bean.getDescription());
            this.priceLabel.setText("€ " + new DecimalFormat("#.00#").format(bean.getPrice()));

            infoWebEngine = infoWebView.getEngine();
            URL url1 = getClass().getResource("info_btn_light.svg");
            infoWebEngine.load(Objects.requireNonNull(url1).toExternalForm());

            int i;

            List<Button> tags = new ArrayList<>();
            tags.add(tag1Btn);
            tags.add(tag2Btn);
            tags.add(tag3Btn);

            for (i=0; i < bean.getTagCount(); i++) {
                tags.get(i).setText(bean.getTag(i));
                tags.get(i).setVisible(true);
            }

            countLabel.setText(Integer.toString(count));

            minusButton.setOnMouseClicked(mouseEvent -> onMinusButton());

            plusButton.setOnMouseClicked(mouseEvent -> onPlusButton());

            if(!bean.isAvailable()) {
                root.setDisable(true);
                notAvailableLabel.setVisible(true);
            }

            this.setGraphic(root);

            this.setFocusTraversable(false);
        } else
            this.setGraphic(null);
    }

    private void onPlusButton() {
        this.getListView().getSelectionModel().clearAndSelect(this.getIndex());
        viewController.onPlusButton();
        count++;
        this.getListView().getSelectionModel().clearSelection();
    }

    private void onMinusButton() {
        if(this.count > 0) {
            this.getListView().getSelectionModel().clearAndSelect(this.getIndex());
            viewController.onMinusButton();
            count--;
            this.getListView().getSelectionModel().clearSelection();
        }
    }

}
