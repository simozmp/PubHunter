package logic.dao;

import logic.exception.DAOException;
import logic.model.Restaurant;
import logic.model.Table;

import java.util.List;

public interface TableDAO extends DAO {
    void insert(Table table) throws DAOException;

    List<Table> readTablesByRestaurant(Restaurant restaurant) throws DAOException;
}
