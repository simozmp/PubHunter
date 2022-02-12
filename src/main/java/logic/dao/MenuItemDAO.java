package logic.dao;

import logic.exception.DAOException;
import logic.model.MenuItem;
import logic.model.Restaurant;

import java.util.List;

/**
 * Data access object class for MenuItem entity class.
 */
public interface MenuItemDAO extends JDBCDataAccessObject {

    /**
     * Insertion operation.
     *
     * @param item the MenuItem instance to be written in the db
     * @param restaurant the restaurant offering to the item
     * @throws DAOException if error occurs. Details in exception message
     */
    void insert(MenuItem item, Restaurant restaurant) throws DAOException;

    /**
     * Reading operation by id of the restaurant.
     *
     * @param id the id of the restaurant offering the requested menu items
     * @return List of requested menuItems
     * @throws DAOException if error occurs. Details in exception message
     */
    List<MenuItem> readMenuItemsByRestaurantId(int id) throws DAOException;

    /**
     * Reading operation.
     *
     * @param item the item to be refreshed
     * @return true if item is found and refreshed, false if item is not found in the db
     * @throws DAOException if error occurs. Details in exception message
     */
    boolean refreshItem(MenuItem item) throws DAOException;

    /**
     * @param item the menu item
     * @return the record id of the restaurant offering menu item
     * @throws DAOException if error occurs. Details in exception message
     */
    int getRestaurantIdForItem(MenuItem item) throws DAOException;

    /**
     * Updating operation. Called if trying to insert an already existing item
     *
     * @param menuItem the item to be updated
     * @param restaurant the restaurant offering the item
     * @throws DAOException if error occurs. Details in exception message
     */
    void updateItem(MenuItem menuItem, Restaurant restaurant) throws DAOException;

    /**
     * Gets item id for a menuItem registered to a restaurant.
     *
     * @throws DAOException if error occurs. Details in exception message
     */
    int getItemId(MenuItem menuItem, Restaurant restaurant) throws DAOException;
}
