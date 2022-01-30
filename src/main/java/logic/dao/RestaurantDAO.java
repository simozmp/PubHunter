package logic.dao;

import logic.dao.implementation.IngredientDAOImpl;
import logic.dao.implementation.MenuItemDAOImpl;
import logic.dao.implementation.TableDAOImpl;
import logic.exception.DAOException;
import logic.model.Restaurant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface RestaurantDAO extends DAO {
    void insert(Restaurant restaurant) throws DAOException;

    Restaurant readRestaurantById(int id) throws DAOException;
}
