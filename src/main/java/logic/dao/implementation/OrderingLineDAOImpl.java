package logic.dao.implementation;

import logic.exception.DAOException;
import logic.model.Ordering;
import logic.model.OrderingLine;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Implementation of an OrderingLineDAO
 */
public class OrderingLineDAOImpl extends JDBCDataAccessObjectImpl implements logic.dao.OrderingLineDAO {
    @Override
    public void insert(OrderingLine line, Ordering ordering) throws DAOException {

        this.connect();

        String query = "INSERT INTO ordering_lines(ordering_fk, menu_item_fk, quantity, notes) VALUES(?,?,?,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, ordering.getRecordId());
            preparedStatement.setInt(2, line.getItemId());
            preparedStatement.setInt(3, line.getQuantity());
            preparedStatement.setString(4, line.getNotes());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Unexpected SQLException while inserting ordering line with message: " + e.getMessage());
        } finally {
            this.disconnect();
        }

        this.disconnect();
    }
}
