package view;


import javafx.collections.ObservableList;
import logic.bean.MenuItemBean;
import logic.bean.OrderingLineBean;

import java.util.Objects;

public abstract class OrderViewControllerImpl implements OrderViewController {

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

    public int getItemOrderingQuantity(MenuItemBean bean) {
        int result = 0;

        for(OrderingLineBean beanIterator : orderingLinesObservableList)
            if(beanIterator.getItemName().equals(bean.getName()))
                result += beanIterator.getQuantity();

        return result;
    }
}
