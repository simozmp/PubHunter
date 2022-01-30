package logic.dao;

import logic.exception.DAOException;
import logic.model.Ordering;
import logic.model.OrderingLine;

public interface OrderingLineDAO extends DAO {
    void insert(OrderingLine line, Ordering ordering) throws DAOException;
}
