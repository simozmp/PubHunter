package view;

import logic.bean.MenuItemBean;

import java.net.URL;
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
import logic.exception.LogicException;
import logic.OrderController;

public class OrderViewController {

    @FXML private Pane outerPane;
    @FXML private Scene outerScene;
    @FXML private VBox outerVBox;
    @FXML private HBox navbarHBox;
    @FXML private Line navbarDividerLine;
    @FXML private WebView logoWebView;
    @FXML private Button navBarMenuBtn;
    @FXML private ListView<MenuItemBean> menuItemsListView;
    @FXML private ListView<String> sectionListView;

    private ObservableList<MenuItemBean> menuItemsObservableList;
    private ObservableList<MenuItemBean> entireMenuObservableList;
    private ObservableList<String> sectionObservableList;

    private OrderController useCaseController;

    @FXML
    private void initialize() {

        WebEngine logoWebEngine;

        String commonStyle = "Alcoholic";

        logoWebEngine = logoWebView.getEngine();
        URL url1 = getClass().getResource("Logo.svg");
        logoWebEngine.load(Objects.requireNonNull(url1).toExternalForm());


        entireMenuObservableList = FXCollections.observableArrayList();

        menuItemsObservableList = FXCollections.observableArrayList();
        menuItemsListView.setItems(menuItemsObservableList);
        menuItemsListView.setCellFactory(listView -> new MenuItemListCell(this));


        sectionObservableList = FXCollections.observableArrayList();
        sectionListView.setItems(sectionObservableList);
        sectionListView.setCellFactory(listView -> new SectionListCellController());
        sectionListView.setOnMouseClicked(mouseEvent -> switchSection());
    }

    public void setMenu(MenuItemBean[] menuItems) {
        entireMenuObservableList.addAll(menuItems);
        updateSections();
    }

    private void updateSections() {
        for (MenuItemBean dishBean : entireMenuObservableList)
            if (!sectionObservableList.contains(dishBean.getCategory()))
                sectionObservableList.add(dishBean.getCategory());
    }

    @FXML
    public void switchSection() {
        String category = sectionListView.getSelectionModel().getSelectedItem();
        showDishesByCategory(category);
    }

    private void showDishesByCategory(String category) {
        menuItemsObservableList.clear();

        for (MenuItemBean dishBean : entireMenuObservableList)
            if (dishBean.getCategory() == category)
                menuItemsObservableList.add(dishBean);
    }

    public void bindController(OrderController controller) throws LogicException {
        if(this.useCaseController == null)
            useCaseController = controller;
        else
            throw new LogicException("Controller already registered on view!");
    }

    public void onPlusButton() {
        try {
            useCaseController.addToOrdering(menuItemsListView.getSelectionModel().getSelectedItem());
        } catch(LogicException e) {
            this.showError(e.getMessage());
        }
    }

    public void onMinusButton() {

    }

    private void showError(String message) {
        // TODO: IMPLEMENT VISUAL ERROR FEEDBACK
        System.out.println("Error: " + message);
    }
}
