package logic.controller;

import logic.bean.MenuItemBean;
import logic.bean.OrderingLineBean;
import logic.bean.TableServiceBean;

import logic.boundary.OperatorBoundary;
import logic.boundary.OperatorBoundaryMock;
import view.OrderViewController;

import logic.dao.MenuItemDAO;
import logic.dao.OrderingDAO;
import logic.dao.RestaurantDAO;
import logic.dao.implementation.MenuItemDAOImpl;
import logic.dao.implementation.OrderingDAOImpl;
import logic.dao.implementation.RestaurantDAOImpl;

import logic.exception.DAOException;
import logic.exception.LogicException;

import logic.model.MenuItem;
import logic.model.Ordering;
import logic.model.Restaurant;
import logic.model.TableService;

import java.util.Objects;

/**
 * Controller class for the order use case
 */
public class OrderController {
    /**
        The TableService to associate ordering/s        */
    private final TableService service;

    /**
        The ordering to build                           */
    private Ordering currentOrdering;

    /**
        The restaurant hosting the service              */
    private Restaurant workingRestaurant;

    /**
        The user boundary viewController                */
    OrderViewController orderViewController;

    /**
     * Controller constructor. This loads the restaurant data from persistence, and sets ready the
     * user boundary view controller.
     *
     * @param orderViewController the user boundary view controller
     * @param service the table service to fill with the new ordering
     */
    public OrderController(OrderViewController orderViewController, TableService service) {

        this.orderViewController = orderViewController;

        try {
            orderViewController.bindUseCaseController(this);
        } catch(LogicException e) {
            orderViewController.showDismissableError("Logic exception" + e.getMessage());
        }

        this.service = service;

        this.currentOrdering = new Ordering(this.service);

        RestaurantDAO restaurantDAO = new RestaurantDAOImpl();
        try {
            this.workingRestaurant = restaurantDAO.readRestaurantById(service.getRestaurantId());
        } catch (DAOException e) {
            orderViewController.showDismissableError("DAOException: " + e.getMessage());
        }

        orderViewController.setService(new TableServiceBean(service));

        MenuItemBean[] menuItems = new MenuItemBean[workingRestaurant.getMenuSize()];

        for(int i=0; i<workingRestaurant.getMenuSize(); i++)
            menuItems[i] = new MenuItemBean(workingRestaurant.getMenuItem(i));

        orderViewController.setMenu(menuItems);
    }

    /**
     * Operation to add the MenuItem matching selectedItemBean to the current ordering.
     *
     * @param selectedItemBean the bean of the item to add to the ordering
     * @throws LogicException if item is not found in restaurant menu
     * @throws DAOException if I/O error occurs when reloading item from persistence
     */
    public void addToOrdering(MenuItemBean selectedItemBean) throws LogicException, DAOException {

        MenuItem item = findWorkingRestaurantMenuItemByBean(selectedItemBean);

        MenuItemDAO menuItemDAO = new MenuItemDAOImpl();

        if(menuItemDAO.refreshItem(item) && item.isAvailable()) {
            if(currentOrdering.add(item))
                orderViewController.setOrdering(beansFromCurrentOrdering());
        } else
            orderViewController.showDismissableError(
                    "Sorry, it seems that " + selectedItemBean.getName() + " is not available anymore.");
    }

    /**
     * Method to remove the MenuItem relative to selectedItemBean from the current ordering.
     *
     * @param selectedItemBean the bean of the item to remove from the ordering
     * @throws LogicException if item is not found in current ordering
     */
    public void removeFromOrdering(MenuItemBean selectedItemBean) throws LogicException {

        MenuItem selectedItem = findWorkingRestaurantMenuItemByBean(selectedItemBean);

        if(currentOrdering.remove(selectedItem)) {
            orderViewController.setOrdering(beansFromCurrentOrdering());
        }
    }

    /**
     * Operation to remove the OrderingLine matching lineBean from the current ordering.
     *
     * @param lineBean the bean of the item to remove from the ordering
     * @throws LogicException if item is not found in current ordering
     */
    public boolean removeOrderingLine(OrderingLineBean lineBean) throws LogicException {
        return currentOrdering.removeLine(lineBean);
    }

    /**
     * Operation to remove all MenuItem-s from current ordering
     */
    public void resetOrdering() {
        this.currentOrdering = new Ordering(this.service);
        orderViewController.setOrdering(null);
    }

    /**
     * Operation to send current ordering to restaurant.
     */
    public void sendOrdering() {
        String message;

        OrderingDAO orderingDAO = new OrderingDAOImpl();

        OperatorBoundary operatorBoundary = new OperatorBoundaryMock(workingRestaurant.getRecordId());

        orderViewController.showDialog("Sending ordering, please wait..");

        currentOrdering.setSent();
        service.addOrdering(currentOrdering);

        try {
            orderingDAO.insert(currentOrdering);
        } catch (DAOException e) {
            orderViewController.showDismissableError("Error writing current ordering in persistence!\n" +
                    "Message: " + e.getMessage());
            return;
        }

        if(operatorBoundary.notifyNewOrdering(beansFromCurrentOrdering(), workingRestaurant.getRecordId())) {
            message = "Ordering has been sent to kitchen.";
            resetOrdering();
        } else
            message = "Restaurant couldn't receive ordering, please try again.";

        orderViewController.dismissDialog();

        orderViewController.showDismissableDialog(message);
    }

    /**
     * Operation to write notes string to a line in the ordering
     *
     * @param text the note to be written
     * @param bean the bean relative to the ordering line
     */
    public void addNotesToOrderingLine(String text, OrderingLineBean bean) {
        for(int i = 0; i<currentOrdering.getLinesCount(); i++)
            if(currentOrdering.getLine(i).getItemName().equals(bean.getItemName()))
                currentOrdering.addNotesToLine(text, i);
    }

    /**
     * @return an array of OrderingLineBean matching current ordering
     */
    private OrderingLineBean[] beansFromCurrentOrdering() {
        OrderingLineBean[] orderingLineBeanList = new OrderingLineBean[currentOrdering.getLinesCount()];

        for(int i=0; i<currentOrdering.getLinesCount(); i++)
            orderingLineBeanList[i] = new OrderingLineBean(currentOrdering.getLine(i));

        return orderingLineBeanList;
    }

    /**
     * Searches for a MenuItem in workingRestaurant matching a bean instance
     *
     * @param selectedItemBean the bean instance
     * @return the MenuItem matching
     * @throws LogicException
     */
    private MenuItem findWorkingRestaurantMenuItemByBean(MenuItemBean selectedItemBean) throws LogicException {
        MenuItem result = null;
        boolean found = false;

        for(int i=0; i<workingRestaurant.getMenuSize(); i++)
            if(Objects.equals(workingRestaurant.getMenuItem(i).getName(), selectedItemBean.getName())) {
                result = workingRestaurant.getMenuItem(i);
                found = true;
            }

        if(!found)
            throw new LogicException("Working with a bean that is not in the menu!");

        return result;
    }
}
