package logic.boundary;

import logic.bean.OrderingLineBean;

public class OperatorBoundaryMock implements OperatorBoundary {

    int restaurantRecordId;

    public OperatorBoundaryMock(int restaurantRecordId) {
        this.restaurantRecordId = restaurantRecordId;
    }

    @Override
    public synchronized boolean sendOrderingBean(OrderingLineBean[] orderingBean) {
        return true;
    }
}
