package logic.dao;

import logic.exception.DAOException;
import logic.model.Ordering;

public interface OrderingDAO extends DAO {
    void insert(Ordering ordering) throws DAOException;
}
