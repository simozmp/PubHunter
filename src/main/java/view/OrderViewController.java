package view;

import logic.Dish;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class OrderViewController {

    @FXML private Pane outerPane;
    @FXML private Scene outerScene;
    @FXML private VBox outerVBox;
    @FXML private HBox navbarHBox;
    @FXML private Line navbarDividerLine;
    @FXML private WebView logoWebView;
    @FXML private Button navBarMenuBtn;
    @FXML private ListView<Dish> dishesListView;
    @FXML private ListView<String> sectionListView;

    @FXML
    private void initialize() {

        ObservableList<Dish> dishObservableList;
        ObservableList<String> sectionObservableList;

        WebEngine logoWebEngine;

        String commonStyle = "Alcoholic";

        logoWebEngine = logoWebView.getEngine();
        URL url1 = getClass().getResource("Logo.svg");
        logoWebEngine.load(Objects.requireNonNull(url1).toExternalForm());

        dishObservableList = FXCollections.observableArrayList();

        ArrayList<String> tags1 = new ArrayList<>();
        tags1.add(commonStyle);
        tags1.add("Cool ;)");
        dishObservableList.add(new Dish("Mojito", "Rum, lime, sugar, mint, soda water", 7, tags1));

        ArrayList<String> tags2 = new ArrayList<>();
        tags2.add(commonStyle);
        tags2.add("Cooler ;)");
        dishObservableList.add(new Dish("Gin Fizz", "Gin, lemon, sugar, carbonated water", 4,  tags2));

        ArrayList<String> tags3 = new ArrayList<>();
        tags3.add(commonStyle);
        tags3.add("Coolest ;)");
        dishObservableList.add(new Dish("Long Island Tea", "Rum, lime, sugar, mint, soda water", 10, tags3));

        dishesListView.setItems(dishObservableList);
        dishesListView.setCellFactory(listView -> new DishListViewCell());


        sectionObservableList = FXCollections.observableArrayList();
        sectionObservableList.addAll("Beverage",
                "Drink",
                "Appetizers",
                "Meals",
                "Fish",
                "Liquors");

        sectionListView.setItems(sectionObservableList);
        sectionListView.setCellFactory(listView -> new SectionListViewCell());
    }
}
