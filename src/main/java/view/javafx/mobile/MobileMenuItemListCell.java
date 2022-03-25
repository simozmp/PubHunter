package view.javafx.mobile;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import logic.bean.MenuItemBean;
import view.javafx.PhotoItemListCell;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MobileMenuItemListCell extends ListCell<MenuItemBean> implements PhotoItemListCell {


    @FXML private AnchorPane root;
    @FXML private ImageView itemImageView;
    @FXML private Label itemNameLabel;
    @FXML private Button plusButton;
    @FXML private Button minusButton;
    @FXML private Label countLabel;
    @FXML private Label priceLabel;
    @FXML private Node itemAccentRectangle;

    private MenuItemBean bean;
    private FXMLLoader fxmlLoader;
    private final MobileOrderViewController viewController;


    public MobileMenuItemListCell(MobileOrderViewController viewController) {
        this.viewController = viewController;
    }

    @Override
    protected void updateItem(MenuItemBean bean, boolean empty) {
        super.updateItem(bean, empty);

        if (bean != null && !empty) {

            //  Saving a reference to the bean
            this.bean = bean;

            //  Loading view
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("mobile-menu-item-list-cell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    Logger.getLogger("JavaFXApplication").log(Level.WARNING, "Couldn''t find fxml view file.", e);
                }
            }

            //  Setting item name and price labels
            this.itemNameLabel.setText(bean.getName());
            this.priceLabel.setText("€" + new DecimalFormat("#.00#").format(bean.getPrice()));

            //  Updating the quantity label
            int quantity = viewController.getItemOrderingQuantity(bean);
            countLabel.setText(quantity + "");

            //  Activating minus button and accent if quantity is more than 0
            if(quantity > 0){
                this.minusButton.setDisable(false);
                this.itemAccentRectangle.setVisible(true);
            }

            //  Buttons bindings
            plusButton.setOnMouseClicked(mouseEvent -> onPlusButton());
            minusButton.setOnMouseClicked(mouseEvent -> onMinusButton());

            drawItemPhoto(itemImageView, bean);

            this.setGraphic(root);

            this.setFocusTraversable(false);

        } else
            this.setGraphic(null);
    }

    @FXML
    private void onPlusButton() {
        this.getListView().getSelectionModel().clearAndSelect(this.getIndex());

        //  Ask to viewController to add item to ordering
        viewController.onPlusButton();

        this.getListView().getSelectionModel().clearSelection();

        //  Enabling the minus button
        this.minusButton.setDisable(false);
        //  Setting accent visible
        this.itemAccentRectangle.setVisible(true);
    }

    @FXML
    private void onMinusButton() {

        //  Verifying that item is in current ordering
        if(viewController.getItemOrderingQuantity(this.bean) > 0) {

            this.getListView().getSelectionModel().clearAndSelect(this.getIndex());

            //  Ask to view controller to remove item from ordering
            viewController.onMinusButton();

            this.getListView().getSelectionModel().clearSelection();

            //  Disabling the minus button and setting accent not visible if quantity is now 0
            if(viewController.getItemOrderingQuantity(this.bean) == 0) {
                this.minusButton.setDisable(true);
                this.itemAccentRectangle.setVisible(false);
            }
        }
    }
}
