package logic.dao.implementation;

import logic.exception.DAOException;
import logic.exception.DAOInsertOnExistingItemException;
import logic.model.Ingredient;
import logic.model.Restaurant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class IngredientDAOImpl extends DAOImpl implements logic.dao.IngredientDAO {

    @Override
    public void insertSupplyToRestaurant(Ingredient supply, Restaurant restaurant) throws DAOException {
        this.connect();

        String query = "INSERT INTO restaurant_supplies(ingredient, restaurant_fk) VALUES (?,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, supply.getDescription().toLowerCase(Locale.ROOT));
            preparedStatement.setInt(2, restaurant.getRecordId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if(e.getMessage().contains("FOREIGN KEY constraint failed"))
                throw new DAOException("Error while inserting a supply into db: restaurant is not bound to a record.");
            else if(e.getMessage().contains("PRIMARY KEY constraint failed"))
                throw new DAOInsertOnExistingItemException("Ingredient is already registered to restaurant");
            else
                throw new DAOException("Unexpected SQLException while inserting into menu_items. " +
                    "Exception message: " + e.getMessage());
        } finally {
            this.disconnect();
        }

        this.disconnect();
    }

    @Override
    public List<Ingredient> getIngredientsByRestaurant(Restaurant restaurant) throws DAOException {

        this.connect();

        ArrayList<Ingredient> ingredients = new ArrayList<>();

        String query = "SELECT ingredient FROM restaurant_supplies WHERE restaurant_fk = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, restaurant.getRecordId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
                ingredients.add(new Ingredient(resultSet.getString("ingredient")));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.disconnect();

        return ingredients;
    }

    @Override
    public List<Ingredient> readIngredientsByItemId(int id) throws DAOException {
        this.connect();

        String query = "SELECT ingredient FROM item_ingredients WHERE menu_item_fk=?";

        ArrayList<Ingredient> ingredientList = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
                ingredientList.add(new Ingredient(resultSet.getString("ingredient")));

        } catch (SQLException e) {
            throw new DAOException("Unexpected SQLException while reading ingredients. Message: " + e.getMessage());
        } finally {
            this.disconnect();
        }

        this.disconnect();

        return ingredientList;
    }

    @Override
    public boolean isIngredientAvailableAtRestaurant(Ingredient ingredient, int restaurantId) throws DAOException {
        this.connect();

        String query = "SELECT * FROM restaurant_supplies WHERE ingredient = ? AND restaurant_fk = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, ingredient.getDescription());
            preparedStatement.setInt(2, restaurantId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
                return false;

        } catch (SQLException e) {
            throw new DAOException("Unexpected SQLException while verifying ingredient availability. Message: " +
                    e.getMessage());
        } finally {
            this.disconnect();
        }

        this.disconnect();

        return true;
    }

    @Override
    public void addIngredientToMenuItemRecord(int itemId, Ingredient ingredient) throws DAOException {
        this.connect();

        String query = "INSERT into item_ingredients(ingredient, menu_item_fk) VALUES (?,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, ingredient.getDescription().toLowerCase(Locale.ROOT));
            preparedStatement.setInt(2, itemId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if(e.getMessage().contains("PRIMARY KEY constraint failed"))
                throw new DAOInsertOnExistingItemException("Ingredient already registered for item " + itemId);
            else
                throw new DAOException("Unexpected SQLException while adding ingredient to menu_item. " +
                        "Message: " + e.getMessage());
        } finally {
            this.disconnect();
        }

        this.disconnect();
    }
}
