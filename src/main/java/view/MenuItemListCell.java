package view;

import logic.bean.MenuItemBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

public class MenuItemListCell extends ListCell<MenuItemBean> {
    @FXML private StackPane root;
    @FXML private WebView infoWebView;
    @FXML private Text nameText;
    @FXML private Text ingredientsText;
    @FXML private Button tag1Btn;
    @FXML private Button tag2Btn;
    @FXML private Button tag3Btn;
    @FXML private Text priceText;
    @FXML private Button minusButton;
    @FXML private Text countText;
    @FXML private Button plusButton;

    private FXMLLoader mLLoader;
    private OrderViewController viewController;

    private int count = 0;

    public MenuItemListCell(OrderViewController viewController) {
        this.viewController = viewController;
    }

    @Override
    protected void updateItem(MenuItemBean dish, boolean empty) {

        WebEngine infoWebEngine;

        super.updateItem(dish, empty);

        if(dish != null && !empty) {

            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("menu-item-list-cell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            this.nameText.setText(dish.getName());
            this.ingredientsText.setText(dish.getDescription());
            this.priceText.setText("€ " + new DecimalFormat("#.00#").format(dish.getPrice()));

            infoWebEngine = infoWebView.getEngine();
            URL url1 = getClass().getResource("info_btn_light.svg");
            infoWebEngine.load(Objects.requireNonNull(url1).toExternalForm());

            int i;

            ArrayList<Button> tags = new ArrayList<>();
            tags.add(tag1Btn);
            tags.add(tag2Btn);
            tags.add(tag3Btn);

            for (i=0; i < dish.getTagCount(); i++) {
                tags.get(i).setText(dish.getTag(i));
                tags.get(i).setVisible(true);
            }

            countText.setText(Integer.toString(count));

            minusButton.setOnMouseClicked(mouseEvent -> {
                this.updateSelected(true);
                viewController.onMinusButton();
            });
            plusButton.setOnMouseClicked(mouseEvent ->  {
                this.updateSelected(true);
                viewController.onPlusButton();
            });

            this.setGraphic(root);

            this.setFocusTraversable(false);
        } else
            this.setGraphic(null);
    }

}
