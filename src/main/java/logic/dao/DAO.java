package logic.dao;

import logic.exception.DAOException;

public interface DAO {

    void connect() throws DAOException;

    void disconnect() throws DAOException;
}
