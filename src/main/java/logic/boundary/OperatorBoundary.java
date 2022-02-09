package logic.boundary;

import logic.bean.OrderingLineBean;

public interface OperatorBoundary {
    boolean sendOrderingBean(OrderingLineBean[] orderingBean);
}
