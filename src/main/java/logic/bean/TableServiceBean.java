package logic.bean;

import logic.model.TableService;

/**
 * Bean class for a TableService class.
 */
public class TableServiceBean {

    /**
     * The name of the restaurant hosting the service
     */
    private final String restaurantName;

    public TableServiceBean(TableService service) {
        this.restaurantName = service.getRestaurantName();
    }

    public String getRestaurantName() {
        return restaurantName;
    }
}
