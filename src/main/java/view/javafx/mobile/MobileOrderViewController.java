package view.javafx.mobile;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import logic.bean.MenuItemBean;
import logic.bean.OrderingLineBean;
import logic.bean.TableServiceBean;
import logic.exception.DAOException;
import logic.exception.LogicException;
import view.javafx.OrderJFXViewController;

import java.util.Objects;

public class MobileOrderViewController extends OrderJFXViewController {

    @FXML private AnchorPane dialogAnchorPane;
    @FXML private Button dismissDialogButton;
    @FXML private Label dialogBodyLabel;
    @FXML private Label dialogTitleLabel;
    @FXML private AnchorPane orderingOverviewAnchorPane;
    @FXML private ListView<String> sectionListView;
    @FXML private ListView<MenuItemBean> itemsListView;
    @FXML private ListView<OrderingLineBean> orderingListView;
    @FXML private Label titleBarLabel;
    @FXML private Label sectionLabel;
    @FXML private Label sectionNameLabel;
    @FXML private HBox titleHBox;
    @FXML private ImageView navigatorSeparationImageView;
    @FXML private Button orderingCountButton;
    @FXML private Button reviewOrderingButton;
    @FXML private Button sendOrderBtn;
    private Hyperlink backHyperlink;

    @FXML
    private void initialize() {
        sectionListView.setVisible(true);
        itemsListView.setVisible(false);
        orderingCountButton.setVisible(false);
        titleBarLabel.setText("Title");

        backHyperlink = new Hyperlink();
        backHyperlink.setPrefWidth(30);
        backHyperlink.setPrefHeight(30);
        backHyperlink.setMaxWidth(30);
        backHyperlink.setMaxHeight(30);
        backHyperlink.setGraphic(new ImageView(new Image(
                Objects.requireNonNull(getClass().getResource("mdi_arrow-left.png")).toExternalForm())));
        backHyperlink.setOnMouseClicked(mouseEvent -> backToSections());

        reviewOrderingButton.setGraphic(new ImageView(new Image(
                Objects.requireNonNull(getClass().
                        getResource("healthicons_i-note-action-outline.png")).toExternalForm())));
        reviewOrderingButton.setOnMouseClicked(mouseEvent -> showOrderingOverview());

        //  Setting up the item counter button for the ordering
        orderingCountButton.setText("0");
        orderingCountButton.setVisible(false);
        orderingLinesObservableList = FXCollections.observableArrayList();
        orderingListView.setItems(orderingLinesObservableList);
        orderingListView.setCellFactory(listView -> new MobileOrderingLineListCell(this));

        //      The following listener will update the latter button to be consistent with data change
        orderingLinesObservableList.addListener(
                (ListChangeListener<? super OrderingLineBean>)
                        orderObservableListChange -> updateOrderingCountButton());

        entireMenuObservableList = FXCollections.observableArrayList();

        menuItemsObservableList = FXCollections.observableArrayList();
        itemsListView.setItems(menuItemsObservableList);
        itemsListView.setCellFactory(listView -> new MobileMenuItemListCell(this));

        sectionObservableList = FXCollections.observableArrayList();
        sectionListView.setItems(sectionObservableList);
        sectionListView.setCellFactory(listView -> new MobileSectionListCell());
        sectionListView.setOnMouseClicked(mouseEvent -> openSection());
        
        sendOrderBtn.setOnMouseClicked(mouseEvent -> useCaseController.sendOrdering());

        dismissDialogButton.setOnMouseClicked(mouseEvent -> onDismissErrorButton());

        sectionLabel.setText("Sections");
        navigatorSeparationImageView.setImage(new Image(Objects.requireNonNull(
                getClass().getResource("navigationSeparator.png")).toExternalForm()));
    }

    private void showOrderingOverview() {
        sectionListView.setVisible(false);
        itemsListView.setVisible(false);
        reviewOrderingButton.setVisible(false);
        orderingCountButton.setVisible(false);
        orderingOverviewAnchorPane.setVisible(true);

        if(!titleHBox.getChildren().contains(backHyperlink))
            titleHBox.getChildren().add(0, backHyperlink);

        sectionLabel.setText("Ordering overview");
        navigatorSeparationImageView.setVisible(false);
        sectionNameLabel.setText("");
    }

    private void openSection() {
        sectionListView.setVisible(false);
        orderingOverviewAnchorPane.setVisible(false);

        reviewOrderingButton.setVisible(true);
        itemsListView.setVisible(true);
        itemsListView.refresh();

        String category = sectionListView.getSelectionModel().getSelectedItem();

        titleHBox.getChildren().add(0, backHyperlink);

        navigatorSeparationImageView.setVisible(true);
        sectionNameLabel.setVisible(true);
        sectionNameLabel.setText(category);

        showItemsByCategory(category);
    }

    private void backToSections() {
        itemsListView.setVisible(false);
        orderingOverviewAnchorPane.setVisible(false);

        reviewOrderingButton.setVisible(true);
        sectionListView.setVisible(true);

        titleHBox.getChildren().remove(0);
        navigatorSeparationImageView.setVisible(false);
        sectionNameLabel.setText("");
        sectionLabel.setText("Sections");
    }

    public void setRestaurantName(String restaurantName) {
        this.titleBarLabel.setText(restaurantName + " · Menu");
    }

    private void updateOrderingCountButton() {
        int count = 0;

        for(OrderingLineBean lineBean : orderingLinesObservableList)
            count += lineBean.getQuantity();

        this.orderingCountButton.setText(Integer.toString(count));
        this.orderingCountButton.setVisible(!orderingLinesObservableList.isEmpty());
    }

    /**
     * Handler of a plus button click for a menu item list cell. In order to be recognized, the item should clear the
     * selection model and select himself.
     */
    public void onPlusButton() {
        try {
            useCaseController.addToOrdering(itemsListView.getSelectionModel().getSelectedItem());
            this.orderingCountButton.setVisible(!orderingLinesObservableList.isEmpty());
        } catch (LogicException e) {
            showDismissableError(e.getMessage());
        }catch (DAOException e) {
            showDismissableError("Error while reading from DB: " + e.getMessage());
        }
    }

    /**
     * Handler of a minus button click for a menu item list cell. In order to be recognized, the item should clear the
     * selection model and select himself.
     */
    public void onMinusButton() {
        try {
            useCaseController.removeFromOrdering(itemsListView.getSelectionModel().getSelectedItem());

            if(orderingLinesObservableList.isEmpty())
                orderingCountButton.setVisible(false);
        } catch(LogicException e) {
            this.showDismissableError(e.getMessage());
        }
    }

    @Override
    public void showDismissableError(String errorMessage) {
        dialogTitleLabel.setText("Error");
        dialogBodyLabel.setText(errorMessage);
        dismissDialogButton.setVisible(true);
        dialogAnchorPane.setVisible(true);
    }

    @Override
    public void showDismissableDialog(String errorMessage) {
        dialogTitleLabel.setText("Dialog");
        dialogBodyLabel.setText(errorMessage);
        dismissDialogButton.setVisible(true);
        dialogAnchorPane.setVisible(true);
    }

    @Override
    public void showDialog(String dialogMessage) {
        dialogTitleLabel.setText("Status");
        dialogBodyLabel.setText(dialogMessage);
        dismissDialogButton.setVisible(false);
        dialogAnchorPane.setVisible(true);
    }

    public void dismissDialog() {
        dialogAnchorPane.setVisible(false);
    }

    private void onDismissErrorButton() {
        dismissDialog();
    }

    @Override
    public void setService(TableServiceBean tableServiceBean) {
        setRestaurantName(tableServiceBean.getRestaurantName());
    }

    @Override
    public void setMenu(MenuItemBean[] allBeans) {
        entireMenuObservableList.addAll(allBeans);

        for (MenuItemBean dishBean : entireMenuObservableList)
            if (!sectionObservableList.contains(dishBean.getCategory()))
                sectionObservableList.add(dishBean.getCategory());
    }

    @Override
    protected void onBackToMenuButton() {
        backToSections();
    }
}
