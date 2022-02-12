package view.javafx.desktop;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import logic.bean.MenuItemBean;
import logic.bean.OrderingLineBean;
import logic.bean.TableServiceBean;
import logic.exception.DAOException;
import logic.exception.LogicException;
import view.javafx.NoSelectionModel;
import view.javafx.OrderJFXViewController;

import java.net.URL;
import java.util.Objects;

public class DesktopOrderViewController extends OrderJFXViewController {

    @FXML private Label dialogTitleLabel;
    @FXML private AnchorPane dialogAnchorPane;
    @FXML private Label dialogBodyLabel;
    @FXML private Button dismissErrorButton;
    @FXML private WebView logoWebView;
    @FXML private Button reviewOrderingButton;
    @FXML private Button backToMenuButton;
    @FXML private ListView<MenuItemBean> menuItemListView;
    @FXML private ListView<String> sectionListView;
    @FXML private ListView<OrderingLineBean> orderingListView;
    @FXML private Button orderingCountButton;
    @FXML private Label containerLabel;
    @FXML private Button sendOrderButton;

    //  Parents that compose the container body
    @FXML private Parent confirmOrderParent;
    @FXML private Parent orderSectionParent;

    @FXML
    private void initialize() {
        WebEngine logoWebEngine;

        logoWebEngine = logoWebView.getEngine();
        URL url1 = getClass().getResource("Logo.svg");
        logoWebEngine.load(Objects.requireNonNull(url1).toExternalForm());

        //  Setting up graphics in reviewOrderingButton
        reviewOrderingButton.setGraphic(new ImageView(new Image(
                Objects.requireNonNull(getClass().getResource("order-icon.png")).toExternalForm())));
        backToMenuButton.setGraphic(new ImageView(new Image(
                Objects.requireNonNull(getClass().getResource("left-arrow.png")).toExternalForm())));

        //  Setting up an observable list for ordered items
        orderingLinesObservableList = FXCollections.observableArrayList();
        orderingListView.setItems(orderingLinesObservableList);
        orderingListView.setFixedCellSize(-1);
        orderingListView.setCellFactory(listview -> new DesktopOrderingLineListCell(this));
        orderingListView.setSelectionModel(new NoSelectionModel<>());

        //  Setting up the item counter button for the ordering
        orderingCountButton.setText("0");
        orderingCountButton.setVisible(false);
        //      The following listener will update the latter button to be consistent with data change
        orderingLinesObservableList.addListener(
                (ListChangeListener<? super OrderingLineBean>) orderObservableListChange -> updateOrderingCountButton());

        entireMenuObservableList = FXCollections.observableArrayList();

        menuItemsObservableList = FXCollections.observableArrayList();
        menuItemListView.setItems(menuItemsObservableList);
        menuItemListView.setCellFactory(listView -> new DesktopMenuItemListCell(this));

        sectionObservableList = FXCollections.observableArrayList();
        sectionListView.setItems(sectionObservableList);
        sectionListView.setCellFactory(listView -> new DesktopSectionListCell());
        sectionListView.setOnMouseClicked(mouseEvent -> switchSection());

        sendOrderButton.setOnMouseClicked(mouseEvent -> useCaseController.sendOrdering());

        dismissErrorButton.setOnMouseClicked(mouseEvent -> dismissDialog());
    }

    public void setRestaurantName(String restaurantName) {
        this.containerLabel.setText(restaurantName + " · Menu");
    }

    @Override
    public void setMenu(MenuItemBean[] allBeans) {
        entireMenuObservableList.addAll(allBeans);
        updateSections();
    }

    private void updateSections() {
        for (MenuItemBean dishBean : entireMenuObservableList)
            if (!sectionObservableList.contains(dishBean.getCategory()))
                sectionObservableList.add(dishBean.getCategory());
    }

    public void onPlusButton() {
        try {
            useCaseController.addToOrdering(menuItemListView.getSelectionModel().getSelectedItem());
        } catch (LogicException e) {
            showDismissableError(e.getMessage());
        } catch (DAOException e) {
            showDismissableError("Error while reading from DB: " + e.getMessage());
        }
    }

    public void onMinusButton() {
        try {
            useCaseController.removeFromOrdering(menuItemListView.getSelectionModel().getSelectedItem());
        } catch(LogicException e) {
            this.showDismissableError(e.getMessage());
        }
    }

    @FXML
    public void onReviewOrderingButton() {
        orderSectionParent.setVisible(false);
        orderSectionParent.setDisable(true);

        confirmOrderParent.setVisible(true);
        confirmOrderParent.setDisable(false);
    }

    @FXML
    public void onBackToMenuButton() {
        confirmOrderParent.setVisible(false);
        confirmOrderParent.setDisable(true);

        menuItemListView.refresh();

        orderSectionParent.setVisible(true);
        orderSectionParent.setDisable(false);
    }

    @FXML
    public void switchSection() {
        String category = sectionListView.getSelectionModel().getSelectedItem();
        showItemsByCategory(category);
    }

    @Override
    public void showDismissableError(String errorMessage) {
        dialogTitleLabel.setText("Error");
        dialogBodyLabel.setText("Message: " + errorMessage);
        dismissErrorButton.setVisible(true);
        dialogAnchorPane.setVisible(true);
    }

    @Override
    public void showDismissableDialog(String dialogMessage) {
        dialogTitleLabel.setText("Dialog");
        dialogBodyLabel.setText(dialogMessage);
        dismissErrorButton.setVisible(true);
        dialogAnchorPane.setVisible(true);
    }

    @Override
    public void showDialog(String errorMessage) {
        dialogTitleLabel.setText("Status");
        dialogBodyLabel.setText("Message: " + errorMessage);
        dismissErrorButton.setVisible(false);
        dialogAnchorPane.setVisible(true);
    }

    @Override
    public void dismissDialog() {
        dialogAnchorPane.setVisible(false);
    }

    @Override
    public void setService(TableServiceBean tableServiceBean) {
        this.setRestaurantName(tableServiceBean.getRestaurantName());
    }

    private void updateOrderingCountButton() {
        int count = 0;

        for(OrderingLineBean lineBean : orderingLinesObservableList)
            count += lineBean.getQuantity();

        this.orderingCountButton.setText(Integer.toString(count));
        this.orderingCountButton.setVisible(!orderingLinesObservableList.isEmpty());
    }
}
