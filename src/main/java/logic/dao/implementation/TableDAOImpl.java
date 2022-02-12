package logic.dao.implementation;

import logic.exception.DAOException;
import logic.exception.DAOInsertOnExistingItemException;
import logic.model.Restaurant;
import logic.model.Table;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a TableDAO
 */
public class TableDAOImpl extends JDBCDataAccessObjectImpl implements logic.dao.TableDAO {

    @Override
    public void insert(Table table) throws DAOException {
        this.connect();

        String query = "INSERT INTO restaurant_tables(id_str, restaurant_fk, seats) values (?,?,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, table.getRepresentation());
            preparedStatement.setInt(2, table.getRestaurantId());
            preparedStatement.setInt(3, table.getSeats());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if(e.getMessage().contains("A PRIMARY KEY constraint failed"))
                throw new DAOInsertOnExistingItemException("Table is already registered for restaurant");
            else
                throw new DAOException("Unexpected SQLException in insertTable@RestaurantDAO with message: "
                        + e.getMessage());
        } finally {
            this.disconnect();
        }

        this.disconnect();
    }

    @Override
    public List<Table> readTablesByRestaurant(Restaurant restaurant) throws DAOException {

        this.connect();

        ArrayList<Table> tables = new ArrayList<>();

        String query = "SELECT id_str, seats FROM restaurant_tables WHERE restaurant_fk=?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, restaurant.getRecordId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
                tables.add(new Table(resultSet.getString("id_str"), resultSet.getInt("seats")));

        } catch (SQLException e) {
            throw new DAOException("Unexpected SQLException while querying restaurant_tables with message: "
                    + e.getMessage());
        }

        this.disconnect();

        return tables;
    }
}
