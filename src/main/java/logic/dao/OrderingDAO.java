package logic.dao;

import logic.exception.DAOException;
import logic.model.Ordering;

/**
 * Data access object class for Ordering entity class.
 */
public interface OrderingDAO extends JDBCDataAccessObject {

    /**
     * Insertion operation.
     *
     * @param ordering the Ordering to be written in the db
     * @throws DAOException if error occurs. Details in exception message
     */
    void insert(Ordering ordering) throws DAOException;
}
