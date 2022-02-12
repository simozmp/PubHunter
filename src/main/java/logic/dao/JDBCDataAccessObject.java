package logic.dao;

import logic.exception.DAOException;

/**
 * Common interface for a jdbc DAO class
 */
public interface JDBCDataAccessObject {

    /**
     * Connects the host to dbms
     *
     * @throws DAOException if error occurs
     */
    void connect() throws DAOException;

    /**
     * Disconnect the host from dbms
     *
     * @throws DAOException if error occurs
     */
    void disconnect() throws DAOException;
}
