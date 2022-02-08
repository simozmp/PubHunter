package logic.dao;

import logic.exception.DAOException;
import logic.model.Restaurant;

public interface RestaurantDAO extends DAO {
    void insert(Restaurant restaurant) throws DAOException;

    Restaurant readRestaurantById(int id) throws DAOException;
}
