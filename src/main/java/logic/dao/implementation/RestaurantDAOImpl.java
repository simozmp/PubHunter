package logic.dao.implementation;

import logic.dao.IngredientDAO;
import logic.dao.MenuItemDAO;
import logic.dao.TableDAO;
import logic.exception.DAOException;
import logic.exception.DAOInsertOnExistingItemException;
import logic.exception.DAOItemNotFoundException;
import logic.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RestaurantDAOImpl extends DAOImpl implements logic.dao.RestaurantDAO {
    @Override
    public void insert(Restaurant restaurant) throws DAOException {

        this.connect();

        String query = "INSERT INTO restaurants(manager, name) VALUES (?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, restaurant.getManagerEmailString());
            preparedStatement.setString(2, restaurant.getName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if(e.getMessage().contains("UNIQUE constraint failed: restaurants.manager, restaurants.name"))
                throw new DAOInsertOnExistingItemException("Restaurant already registered in db");
            else
                throw new DAOException("Unexpected SQLException while writing restaurant into db." +
                        "Message: " + e.getMessage());
        } finally {
            this.disconnect();
            writeRestaurantData(restaurant);
        }

        this.disconnect();
        writeRestaurantData(restaurant);
    }

    @Override
    public Restaurant readRestaurantById(int id) throws DAOException {
        this.connect();
        
        String query = "SELECT * FROM restaurants WHERE id=?";

        ResultSet restaurantResultSet = null;

        String restaurantName = "";
        
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            restaurantResultSet = preparedStatement.executeQuery();

            if(!restaurantResultSet.next())
                throw new DAOItemNotFoundException("Restaurant with id " + id + " has not been found in database.");

            restaurantName = restaurantResultSet.getString("name");

        } catch (SQLException e) {
            throw new DAOException("Unexpected SQLException while querying restaurant id with message: "
                    + e.getMessage());
        } finally {
            this.disconnect();
        }

        this.disconnect();

        //      Building the resulting restaurant instance

        //  A UserDAO to load the manager
        UserDAOImpl userDAO = new UserDAOImpl();

        //  Instantiating the restaurant
        Restaurant resultRestaurant = new Restaurant(restaurantName, userDAO.getManagerByRestaurantId(id));
        //  Binding to persistence
        resultRestaurant.setRecordId(id);

        //  A TableDAO to load the restaurant tables
        TableDAOImpl tableDAO = new TableDAOImpl();
        //  Loading and adding tables to the restaurant
        for(Table table : tableDAO.readTablesByRestaurant(resultRestaurant))
            resultRestaurant.addTable(table);

        //  A MenuItemDAO to load the menu items
        MenuItemDAOImpl menuItemDAO = new MenuItemDAOImpl();
        //  Loading and adding menu items to the restaurant
        for(MenuItem item : menuItemDAO.readMenuItemsByRestaurantId(id))
            resultRestaurant.addMenuItem(item);

        //  An IngredientDAO to load the restaurant supplies
        IngredientDAOImpl ingredientDAO = new IngredientDAOImpl();
        //  Loading and adding supplies to the restaurant
        for(Ingredient supply : ingredientDAO.getIngredientsByRestaurant(resultRestaurant))
            resultRestaurant.addSupply(supply);

        return resultRestaurant;
    }

    private int getRestaurantIdByName(String name, String managerEmail) throws DAOException {

        int id = -1;

        this.connect();

        String query2 = "SELECT id FROM restaurants WHERE name=? AND manager=?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query2)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, managerEmail);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
                throw new DAOException("Error while writing restaurant!");

            id = resultSet.getInt("id");
        } catch (SQLException e) {
            throw new DAOException("Unexpected SQLException while retrieving written restaurant id! " +
                    "Message: " + e.getMessage());
        } finally {
            this.disconnect();
        }

        this.disconnect();

        return id;
    }

    private void writeRestaurantData(Restaurant restaurant) throws DAOException {

        restaurant.setRecordId(getRestaurantIdByName(restaurant.getName(), restaurant.getManagerEmailString()));

        MenuItemDAO menuItemDAO = new MenuItemDAOImpl();
        IngredientDAO ingredientDAO = new IngredientDAOImpl();
        TableDAO tableDAO = new TableDAOImpl();

        for(int i=0; i<restaurant.getMenuSize(); i++)
            try {
                menuItemDAO.insert(restaurant.getMenuItem(i), restaurant);
            } catch(DAOInsertOnExistingItemException e) {
                menuItemDAO.getItemId(restaurant.getMenuItem(i), restaurant);
                menuItemDAO.updateItem(restaurant.getMenuItem(i), restaurant);
            }


        for(int i=0; i<restaurant.getTablesCount(); i++)
            try {
                tableDAO.insert(restaurant.getTable(i));
            } catch (DAOInsertOnExistingItemException ignored) {
            }

        for(int i=0; i<restaurant.getSuppliesCount(); i++)
            try {
                ingredientDAO.insertSupplyToRestaurant(restaurant.getSupply(i), restaurant);
            } catch (DAOInsertOnExistingItemException ignored) {
            }
    }
}
