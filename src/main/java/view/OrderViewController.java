package view;

import logic.bean.MenuItemBean;
import logic.bean.OrderingLineBean;
import logic.bean.TableServiceBean;
import logic.controller.OrderController;
import logic.exception.LogicException;

public interface OrderViewController {
    void bindUseCaseController(OrderController orderController) throws LogicException;

    void showError(String s);

    void setService(TableServiceBean tableServiceBean);

    void setMenu(MenuItemBean[] menuItems);

    void onRemoveOrderingLineButton(OrderingLineBean bean);

    void addNotesToLine(String notes, OrderingLineBean orderingBean);

    void setOrdering(OrderingLineBean[] bean);
}
