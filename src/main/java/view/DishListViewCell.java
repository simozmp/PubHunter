package view;

import logic.Dish;
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

public class DishListViewCell extends ListCell<Dish> {
    @FXML private StackPane root;
    @FXML private WebView infoWebView;
    @FXML private Text nameText;
    @FXML private Text ingredientsText;
    @FXML private Button tag1;
    @FXML private Button tag2;
    @FXML private Button tag3;
    @FXML private Text priceText;
    @FXML private Button minusButton;
    @FXML private Text countText;
    @FXML private Button plusButton;

    private FXMLLoader mLLoader;

    private int count = 0;

    @Override
    protected void updateItem(Dish dish, boolean empty) {

        WebEngine infoWebEngine;

        super.updateItem(dish, empty);

        if(dish != null && !empty) {

            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("menu-list-cell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            this.nameText.setText(dish.getName());
            this.ingredientsText.setText(dish.getIngredients());
            this.priceText.setText("€ " + new DecimalFormat("#.00#").format(dish.getPrice()));

            infoWebEngine = infoWebView.getEngine();
            URL url1 = getClass().getResource("info_btn_light.svg");
            infoWebEngine.load(Objects.requireNonNull(url1).toExternalForm());

            int i;

            ArrayList<Button> tags = new ArrayList<>();
            tags.add(tag1);
            tags.add(tag2);
            tags.add(tag3);

            for (i = 0; i < dish.getTagCount(); i++)
                tags.get(i).setText(dish.getTag(i));

            minusButton.setOnMouseClicked(mouseEvent -> {
                if(count>0) {
                    count = count - 1;
                    //  TODO: Remove dish to order
                    countText.setText(Integer.toString(count));
                }
            });

            countText.setText(Integer.toString(count));

            plusButton.setOnMouseClicked(mouseEvent -> {
                count = count + 1;
                //  TODO: Add dish to order
                countText.setText(Integer.toString(count));
            });

            this.setGraphic(root);

            this.setFocusTraversable(false);
        } else
            this.setGraphic(null);
    }

}
