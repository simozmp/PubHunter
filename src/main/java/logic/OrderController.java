package logic;

import logic.bean.MenuItemBean;
import logic.exception.LogicException;
import view.OrderViewController;

public class OrderController {
    private Booking booking;
    private Ordering currentOrdering;
    private Restaurant workingRestaurant;

    OrderViewController orderViewController;

    public OrderController(OrderViewController viewController, Booking booking) {
        try {
            viewController.bindController(this);
        } catch(LogicException e) {
            System.out.println(e.getMessage());
        }

        this.orderViewController = viewController;

        this.booking = booking;
        this.currentOrdering = new Ordering();
        this.workingRestaurant = booking.getRestaurant();

        MenuItemBean[] menuItems = new MenuItemBean[workingRestaurant.getMenuSize()];

        MenuItem itemIterator;

        for(int i=0; i<workingRestaurant.getMenuSize(); i++) {
            itemIterator = workingRestaurant.getMenuItem(i);

            menuItems[i] = new MenuItemBean(itemIterator.getName(),
                    itemIterator.getDescription(),
                    itemIterator.getPrice(),
                    itemIterator.getTags(),
                    itemIterator.isAvailable(),
                    itemIterator.getCategory());
        }

        viewController.setMenu(menuItems);
    }

    public void addToOrdering(MenuItemBean selectedItemBean) throws LogicException {
        MenuItem item = getMenuItemFromBean(selectedItemBean);
        currentOrdering.add(item);
    }

    public void resetOrdering() {
        this.currentOrdering.clear();
    }

    public void sendOrdering() {
        this.booking.getService().addOrdering(currentOrdering);
    }

    private MenuItem getMenuItemFromBean(MenuItemBean bean) throws LogicException {
        int i;
        int menuSize = workingRestaurant.getMenuSize();

        for(i=0; i<menuSize; i++)
            if(bean.getName() == workingRestaurant.getMenuItem(i).getName())
                break;

        if(i == menuSize)
            throw new LogicException("MenuItem not found from bean.");

        return workingRestaurant.getMenuItem(i);
    }
}
