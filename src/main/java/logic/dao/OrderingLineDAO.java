package logic.dao;

import logic.exception.DAOException;
import logic.model.Ordering;
import logic.model.OrderingLine;

/**
 * Data access object class for OrderingLine entity class.
 */
public interface OrderingLineDAO extends JDBCDataAccessObject {

    /**
     * Insertion operation.
     *
     * @param line the OrderingLine instance to be written in the db
     * @param ordering the Ordering composing line part
     * @throws DAOException if error occurs. Details in exception message
     */
    void insert(OrderingLine line, Ordering ordering) throws DAOException;
}
