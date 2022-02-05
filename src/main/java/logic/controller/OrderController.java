package logic.controller;

import logic.bean.MenuItemBean;
import logic.bean.OrderingLineBean;
import logic.bean.TableServiceBean;
import logic.dao.implementation.RestaurantDAOImpl;
import logic.exception.DAOException;
import logic.exception.LogicException;
import logic.model.*;
import view.OrderViewController;

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
            System.out.println("Logic exception" + e.getMessage());
            e.printStackTrace();
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

    public void addToOrdering(MenuItemBean selectedItemBean) {

        MenuItem item = selectedItemBean.getReference();

        if(item.updateFromPersistence() && item.isAvailable()) {
            if(currentOrdering.add(item))
                orderViewController.setOrdering(beansFromCurrentOrdering());
        } else
            orderViewController.showItemNotAvailableError(selectedItemBean);
    }

    public void removeFromOrdering(MenuItemBean selectedItemBean) throws LogicException {
        MenuItem selectedItem = selectedItemBean.getReference();

        if(currentOrdering.remove(selectedItem)) {
            orderViewController.setOrdering(beansFromCurrentOrdering());
        }
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
        OrderingLineBean[] beansList = new OrderingLineBean[currentOrdering.getLinesCount()];

        for(int i=0; i<currentOrdering.getLinesCount(); i++)
            beansList[i] = new OrderingLineBean(currentOrdering.getLine(i));

        return beansList;
    }

}
