package view;

import logic.bean.MenuItemBean;
import logic.bean.TableServiceBean;
import logic.controller.OrderController;
import logic.exception.LogicException;

public interface OrderViewController {
    void bindUseCaseController(OrderController orderController) throws LogicException;

    void showError(String s);

    void setService(TableServiceBean tableServiceBean);

    void setMenu(MenuItemBean[] menuItems);

    void addToOrdered(MenuItemBean selectedItemBean);

    void showItemNotAvailableError(MenuItemBean selectedItemBean);

    void removeFromOrdered(MenuItemBean selectedItemBean);
}
