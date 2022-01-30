package logic.dao;

import logic.exception.DAOException;
import logic.model.MenuItem;
import logic.model.Restaurant;

import java.util.List;

public interface MenuItemDAO extends DAO {
    void insert(MenuItem item, Restaurant restaurant) throws DAOException;

    List<MenuItem> readMenuItemsByRestaurantId(int id) throws DAOException;

    boolean refreshItem(MenuItem item) throws DAOException;

    int getRestaurantIdForItem(MenuItem item) throws DAOException;

    void updateItem(MenuItem menuItem, Restaurant restaurant) throws DAOException;

    int getItemId(MenuItem menuItem, Restaurant restaurant) throws DAOException;
}
