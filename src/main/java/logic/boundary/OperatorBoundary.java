package logic.boundary;

import logic.bean.OrderingLineBean;
import logic.model.Restaurant;

/**
 * Simple interface defining expected operations to implement in an OperatorBoundary
 */
public interface OperatorBoundary {

    /**
     * Operation is meant to send a "new ordering" notification to all hosts registered as
     * operator working device for given restaurant.
     *
     * @param orderingBean a list of OrderingLineBean to be sent.
     * @param restaurantRecordId the persistence id of recipient restaurant
     * @return true if restaurant has been successfully notified, false otherwise
     */
    boolean notifyNewOrdering(OrderingLineBean[] orderingBean, int restaurantRecordId);
}
