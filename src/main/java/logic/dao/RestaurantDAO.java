package logic.dao;

import logic.exception.DAOException;
import logic.model.Restaurant;

/**
 * Data access object class for Restaurant entity class.
 */
public interface RestaurantDAO extends JDBCDataAccessObject {

    /**
     * Insertion operation.
     *
     * @param restaurant the Restaurant instance to be written in the db
     * @throws DAOException if error occurs. Details in exception message
     */
    void insert(Restaurant restaurant) throws DAOException;

    /**
     * Reading operation.
     *
     * @param id the record id of the requested restaurant
     * @throws DAOException if error occurs. Details in exception message
     */
    Restaurant readRestaurantById(int id) throws DAOException;
}
