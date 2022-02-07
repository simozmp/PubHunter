package logic.controller;

import logic.bean.MenuItemBean;
import logic.bean.OrderingLineBean;
import logic.bean.TableServiceBean;
import logic.dao.implementation.RestaurantDAOImpl;
import logic.exception.DAOException;
import logic.exception.LogicException;
import logic.model.*;
import view.OrderViewController;

import java.util.Objects;

public class OrderController {
    private final TableService service;
    private final Ordering currentOrdering;
    private Restaurant workingRestaurant;

    OrderViewController orderViewController;

    public OrderController(OrderViewController orderViewController, TableService service) {

        this.orderViewController = orderViewController;

        try {
            orderViewController.bindUseCaseController(this);
        } catch(LogicException e) {
            orderViewController.showError("Logic exception" + e.getMessage());
        }

        this.service = service;

        this.currentOrdering = new Ordering(service);

        RestaurantDAOImpl restaurantDAO = new RestaurantDAOImpl();
        try {
            this.workingRestaurant = restaurantDAO.readRestaurantById(service.getRestaurantId());
        } catch (DAOException e) {
            orderViewController.showError("DAOException: " + e.getMessage());
        }

        orderViewController.setService(new TableServiceBean(service));

        MenuItemBean[] menuItems = new MenuItemBean[workingRestaurant.getMenuSize()];

        for(int i=0; i<workingRestaurant.getMenuSize(); i++)
            menuItems[i] = new MenuItemBean(workingRestaurant.getMenuItem(i));

        orderViewController.setMenu(menuItems);
    }

    public void addToOrdering(MenuItemBean selectedItemBean) throws LogicException {

        MenuItem item = findWorkingRestaurantMenuItemByBean(selectedItemBean);

        if(item.updateFromPersistence() && item.isAvailable()) {
            if(currentOrdering.add(item))
                orderViewController.setOrdering(beansFromCurrentOrdering());
        } else
            orderViewController.showError(
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
        this.currentOrdering.clear();
    }

    public void sendOrdering() {
        try {
            this.service.addOrdering(currentOrdering);
        } catch (LogicException e) {
            orderViewController.showError(e.getMessage());
        }
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
