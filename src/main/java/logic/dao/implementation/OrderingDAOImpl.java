package logic.dao.implementation;

import logic.exception.DAOException;
import logic.model.Ordering;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderingDAOImpl extends DAOImpl implements logic.dao.OrderingDAO {
    @Override
    public void insert(Ordering ordering) throws DAOException {

        //  First query: insertion of the record
        this.connect();

        String query = "INSERT INTO service_orderings(table_service_fk, sent_date, sent_time) values(?,?,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, ordering.getServiceId());
            preparedStatement.setString(2, ordering.getSentDate().toString());
            preparedStatement.setString(3, ordering.getSentTime().toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Unexpected SQLException while inserting ordering with message: " + e.getMessage());
        } finally {
            this.disconnect();
        }

        this.disconnect();

        //  Second query: a select to get the record id, and to set it into the object
        this.connect();

        query = "SELECT id, table_service_fk FROM service_orderings WHERE table_service_fk = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, ordering.getServiceId());

            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
                throw new DAOException("Error in inserting ordering: the record has successfully been written, " +
                        "but querying it gave no result.");

            ordering.setRecordId(resultSet.getInt("id"));

        } catch (SQLException e) {
            throw new DAOException("Unexpected SQLException while inserting ordering with message: " + e.getMessage());
        } finally {
            this.disconnect();
        }

        this.disconnect();


        OrderingLineDAOImpl orderingLineDAO = new OrderingLineDAOImpl();

        for(int i = 0; i<ordering.getLinesCount(); i++)
            orderingLineDAO.insert(ordering.getLine(i), ordering);
    }
}
