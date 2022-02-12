package logic.dao;

import logic.exception.DAOException;
import logic.model.Restaurant;
import logic.model.Table;

import java.util.List;

/**
 * Data access object class for Table entity class.
 */
public interface TableDAO extends JDBCDataAccessObject {

    /**
     * Insertion operation.
     *
     * @param table the Table instance to be written in the db
     * @throws DAOException if error occurs. Details in exception message
     */
    void insert(Table table) throws DAOException;

    /**
     * Reading operation.
     *
     * @param restaurant requested tables' restaurant record id
     * @return a List of requested Table-s
     * @throws DAOException
     */
    List<Table> readTablesByRestaurant(Restaurant restaurant) throws DAOException;
}
