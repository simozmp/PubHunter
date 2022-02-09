package logic.dao;

import logic.exception.DAOException;
import logic.model.TableService;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface TableServiceDAO {
    void insert(TableService tableService) throws DAOException;

    void prepareStatementForTableService(TableService tableService, PreparedStatement preparedStatement)
            throws SQLException;
}
