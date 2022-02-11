package logic.controller;

import logic.bean.MenuItemBean;
import logic.bean.OrderingLineBean;
import logic.bean.TableServiceBean;
import logic.boundary.OperatorBoundary;
import logic.boundary.OperatorBoundaryMock;
import logic.dao.MenuItemDAO;
import logic.dao.OrderingDAO;
import logic.dao.implementation.MenuItemDAOImpl;
import logic.dao.implementation.OrderingDAOImpl;
import logic.dao.implementation.RestaurantDAOImpl;
import logic.exception.DAOException;
import logic.exception.LogicException;
import logic.model.MenuItem;
import logic.model.Ordering;
import logic.model.Restaurant;
import logic.model.TableService;
import view.OrderViewController;

import java.util.Objects;

public class OrderController {
    private final TableService service;
    private Ordering currentOrdering;
    private Restaurant workingRestaurant;

    OrderViewController orderViewController;

    public OrderController(OrderViewController orderViewController, TableService service) {

        this.orderViewController = orderViewController;

        try {
            orderViewController.bindUseCaseController(this);
        } catch(LogicException e) {
            orderViewController.showDismissableError("Logic exception" + e.getMessage());
        }

        this.service = service;

        this.currentOrdering = new Ordering(this.service);

        RestaurantDAOImpl restaurantDAO = new RestaurantDAOImpl();
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

    public void removeFromOrdering(MenuItemBean selectedItemBean) throws LogicException {

        MenuItem selectedItem = findWorkingRestaurantMenuItemByBean(selectedItemBean);

        if(currentOrdering.remove(selectedItem)) {
            orderViewController.setOrdering(beansFromCurrentOrdering());
        }
    }

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

    public boolean removeOrderingLine(OrderingLineBean lineBean) throws LogicException {
        return currentOrdering.removeLine(lineBean);
    }

    public void resetOrdering() {
        this.currentOrdering = new Ordering(this.service);
        orderViewController.setOrdering(null);
    }

    public void sendOrdering() {
        String message;

        OrderingDAO orderingDAO = new OrderingDAOImpl();

        OperatorBoundary operatorBoundary = new OperatorBoundaryMock(workingRestaurant.getRecordId());

        orderViewController.showDialog("Sending order, please wait..");

        currentOrdering.setSent();

        try {
            orderingDAO.insert(currentOrdering);
        } catch (DAOException e) {
            orderViewController.showDismissableError("Error writing current ordering in persistence!\n" +
                    "Message: " + e.getMessage());
            return;
        }

        if(operatorBoundary.sendOrderingBean(beansFromCurrentOrdering())) {
            message = "Order has been sent to kitchen.";
            resetOrdering();
        } else
            message = "Restaurant couldn't receive ordering, please try again.";

        orderViewController.dismissDialog();

        orderViewController.showDismissableDialog(message);
    }

    private OrderingLineBean[] beansFromCurrentOrdering() {
        OrderingLineBean[] orderingLineBeanList = new OrderingLineBean[currentOrdering.getLinesCount()];

        for(int i=0; i<currentOrdering.getLinesCount(); i++)
            orderingLineBeanList[i] = new OrderingLineBean(currentOrdering.getLine(i));

        return orderingLineBeanList;
    }

    public void addNotesToOrderingLine(String text, OrderingLineBean bean) {
        for(int i = 0; i<currentOrdering.getLinesCount(); i++)
            if(currentOrdering.getLine(i).getItemName().equals(bean.getItemName()))
                currentOrdering.addNotesToLine(text, i);
    }
}
