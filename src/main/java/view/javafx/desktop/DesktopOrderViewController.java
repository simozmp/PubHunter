package view.javafx.desktop;

import javafx.collections.ListChangeListener;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import logic.bean.OrderingLineBean;
import logic.bean.TableServiceBean;
import logic.exception.LogicException;
import logic.controller.OrderController;
import view.OrderViewController;

public class DesktopOrderViewController implements OrderViewController {

    @FXML private Pane outerPane;
    @FXML private Scene outerScene;
    @FXML private VBox outerVBox;
    @FXML private HBox navbarHBox;
    @FXML private Line navbarDividerLine;
    @FXML private WebView logoWebView;
    @FXML private Button navBarMenuBtn;
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

    private TableServiceBean serviceBean;
    private ObservableList<MenuItemBean> menuItemsObservableList;
    private ObservableList<MenuItemBean> entireMenuObservableList;
    private ObservableList<OrderingLineBean> orderingLinesObservableList;
    private ObservableList<String> sectionObservableList;

    private OrderController useCaseController;

    @FXML
    private void initialize() {
        WebEngine logoWebEngine;

        logoWebEngine = logoWebView.getEngine();
        URL url1 = getClass().getResource("Logo.svg");
        logoWebEngine.load(Objects.requireNonNull(url1).toExternalForm());

        //  Setting up graphics in reviewOrderingButton
        reviewOrderingButton.setGraphic(
                new ImageView(new Image(getClass().getResource("order-icon.png").toExternalForm())));
        backToMenuButton.setGraphic(
                new ImageView(new Image(getClass().getResource("left-arrow.png").toExternalForm())));

        //  Setting up an observable list for ordered items
        orderingLinesObservableList = FXCollections.observableArrayList();
        orderingListView.setItems(orderingLinesObservableList);
        orderingListView.setFixedCellSize(-1);
        orderingListView.setCellFactory(listview -> new OrderingLineListCell(this));
        orderingListView.setSelectionModel(new NoSelectionModel());

        //  Setting up the item counter button for the ordering
        orderingCountButton.setText("0");
        orderingCountButton.setVisible(false);
        //      The following listener will update the latter button to be consistent with data change
        orderingLinesObservableList.addListener(
                (ListChangeListener<? super OrderingLineBean>) orderObservableListChange -> updateOrderingCountButton());

        entireMenuObservableList = FXCollections.observableArrayList();

        menuItemsObservableList = FXCollections.observableArrayList();
        menuItemListView.setItems(menuItemsObservableList);
        menuItemListView.setCellFactory(listView -> new MenuItemListCell(this));

        sectionObservableList = FXCollections.observableArrayList();
        sectionListView.setItems(sectionObservableList);
        sectionListView.setCellFactory(listView -> new SectionListCellController());
        sectionListView.setOnMouseClicked(mouseEvent -> switchSection());

        sendOrderButton.setOnMouseClicked(mouseEvent -> useCaseController.sendOrdering());
    }

    public void setRestaurantName(String restaurantName) {
        this.containerLabel.setText(restaurantName + " · Menu");
    }

    public void bindUseCaseController(OrderController controller) throws LogicException {
        if(this.useCaseController == null)
            useCaseController = controller;
        else
            throw new LogicException("Controller already registered on view!");
    }

    public void setMenu(MenuItemBean[] allBeans) {
        entireMenuObservableList.addAll(allBeans);
        updateSections();
    }

    @Override
    public void setOrdering(OrderingLineBean[] allBeans) {
        orderingLinesObservableList.setAll(allBeans);
    }

    private void updateSections() {
        for (MenuItemBean dishBean : entireMenuObservableList)
            if (!sectionObservableList.contains(dishBean.getCategory()))
                sectionObservableList.add(dishBean.getCategory());
    }

    public void onPlusButton() {
        useCaseController.addToOrdering(menuItemListView.getSelectionModel().getSelectedItem());
    }

    public void onMinusButton() {
        try {
            useCaseController.removeFromOrdering(menuItemListView.getSelectionModel().getSelectedItem());
        } catch(LogicException e) {
            this.showError(e.getMessage());
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

    public void showError(String message) {
        // TODO: IMPLEMENT VISUAL ERROR FEEDBACK
        System.out.println("Error: " + message);
    }

    public void showItemNotAvailableError(MenuItemBean selectedItemBean) {
        //  TODO: IMPLEMENT
        /*
            The UI shall only be communicating that the item is not available anymore, and should not need to update
            the listview representation. That is 'cause the bean (implementer of PropertyChangeListener) should be
            updated already, along with his graphical representation (MenuItemListCell)
         */

        showError("Item \"" + selectedItemBean.getName() +"\" is not available anymore.");
    }

    public void setService(TableServiceBean tableServiceBean) {
        this.serviceBean = tableServiceBean;
        this.setRestaurantName(serviceBean.getRestaurantName());
    }

    private void showItemsByCategory(String category) {
        menuItemsObservableList.clear();

        for (MenuItemBean bean : entireMenuObservableList)
            if (Objects.equals(bean.getCategory(), category)) {
                menuItemsObservableList.add(bean);
            }
    }

    private void updateOrderingCountButton() {
        int count = 0;

        for(OrderingLineBean lineBean : orderingLinesObservableList)
            count += lineBean.getQuantity();

        this.orderingCountButton.setText(Integer.toString(count));
        this.orderingCountButton.setVisible(!orderingLinesObservableList.isEmpty());
    }

    public int getItemOrderingCount(MenuItemBean bean) {
        int result = 0;

        for(OrderingLineBean beanIterator : orderingLinesObservableList)
            if(beanIterator.getItemName().equals(bean.getName()))
                result += beanIterator.getQuantity();

        return result;
    }

    public void removeOrdering(OrderingLineBean bean) {
        if(!orderingLinesObservableList.remove(bean))
            showError("Trying to remove an OrderingLineBean which is not in the list");
    }
}
