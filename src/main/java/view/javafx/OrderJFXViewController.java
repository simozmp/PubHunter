package view.javafx;


import javafx.collections.ObservableList;
import logic.bean.MenuItemBean;
import logic.bean.OrderingLineBean;
import logic.controller.OrderController;
import logic.exception.LogicException;
import view.OrderViewController;

import java.util.Objects;

public abstract class OrderJFXViewController implements OrderViewController {

    protected OrderController useCaseController;

    protected ObservableList<MenuItemBean> menuItemsObservableList;
    protected ObservableList<MenuItemBean> entireMenuObservableList;
    protected ObservableList<OrderingLineBean> orderingLinesObservableList;
    protected ObservableList<String> sectionObservableList;

    protected void showItemsByCategory(String category) {
        menuItemsObservableList.clear();

        for (MenuItemBean bean : entireMenuObservableList)
            if (Objects.equals(bean.getCategory(), category)) {
                menuItemsObservableList.add(bean);
            }
    }

    protected abstract void onBackToMenuButton();

    @Override
    public void bindUseCaseController(OrderController controller) throws LogicException {
        if(this.useCaseController == null)
            useCaseController = controller;
        else
            throw new LogicException("Controller already registered on view!");
    }

    @Override
    public void setOrdering(OrderingLineBean[] allBeans) {
        orderingLinesObservableList.clear();

        if(allBeans != null)
            orderingLinesObservableList.setAll(allBeans);
        else
            onBackToMenuButton();
    }


    public int getItemOrderingQuantity(MenuItemBean bean) {
        int result = 0;

        for(OrderingLineBean beanIterator : orderingLinesObservableList)
            if(beanIterator.getItemName().equals(bean.getName()))
                result += beanIterator.getQuantity();

        return result;
    }

    @Override
    public void onRemoveOrderingLineButton(OrderingLineBean bean) {
        try {
            if(useCaseController.removeOrderingLine(bean)) {
                if (!orderingLinesObservableList.remove(bean))
                    showDismissableError("Trying to remove an OrderingLineBean which is not in the list");
            } else
                showDismissableError("Logic error while removing ordering line!");
        } catch (LogicException e) {
            showDismissableError("Logic error while removing ordering line");
        }
    }
}
