package logic.dao;

import logic.exception.DAOException;
import logic.model.TableService;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Data access object class for TableService entity class.
 */
public interface TableServiceDAO {

    /**
     * Insertion operation.
     *
     * @param tableService the TableService instance to be written in the db
     * @throws DAOException if error occurs. Details in exception message
     */
    void insert(TableService tableService) throws DAOException;
}
