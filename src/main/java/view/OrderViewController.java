package view;

import logic.bean.MenuItemBean;
import logic.bean.OrderingLineBean;
import logic.bean.TableServiceBean;
import logic.controller.OrderController;
import logic.exception.LogicException;

public abstract class OrderViewController {

    protected OrderController useCaseController;

    public abstract void bindUseCaseController(OrderController orderController) throws LogicException;

    public abstract void showDismissableError(String errorMessage);

    public abstract void showDialog(String dialogMessage);

    public abstract void showDismissableDialog(String dialogMessage);

    public abstract void dismissDialog();

    public abstract void setService(TableServiceBean tableServiceBean);

    public abstract void setMenu(MenuItemBean[] menuItems);

    public abstract void setOrdering(OrderingLineBean[] bean);
}
