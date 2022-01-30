package logic.dao.implementation;

import logic.dao.DAO;
import logic.dao.IngredientDAO;
import logic.exception.DAOException;
import logic.exception.DAOInsertOnExistingItemException;
import logic.exception.DAOItemNotFoundException;
import logic.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class MenuItemDAOImpl extends DAOImpl implements logic.dao.MenuItemDAO {

    private static final String CATEGORY_COLUMN_STRING = "category";
    private static final String DESCRIPTION_COLUMN_STRING = "description";
    private static final String PRICE_COLUMN_STRING = "price";

    @Override
    public void insert(MenuItem item, Restaurant restaurant) throws DAOException {
        this.connect();

        String query = "INSERT INTO menu_items(name, price, availability, category, " +
                "description, tags, the_cocktail_db_drink_id, restaurant_fk) " +
                "VALUES (?,?,?,?,?,?,?,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setDouble(2, item.getPrice());
            preparedStatement.setString(4, item.getCategory());
            preparedStatement.setString(5, item.getDescription());

            StringBuilder tagStringBuilder = new StringBuilder();
            for(int i=0; i<item.getTagCount(); i++)
                (i != 0 ? tagStringBuilder.append(", ") : tagStringBuilder).append(item.getTag(i));

            preparedStatement.setString(6, tagStringBuilder.toString());

            if(item instanceof DrinkItem drink) {
                preparedStatement.setBoolean(3, true);
                preparedStatement.setInt(7, drink.getTheCocktailDBId());
            } else {
                preparedStatement.setBoolean(3, item.isAvailable());
                preparedStatement.setInt(7, -1);
            }

            preparedStatement.setInt(8, restaurant.getRecordId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if(e.getMessage().contains("UNIQUE constraint failed")) {
                postInsert(item, restaurant);
                throw new DAOInsertOnExistingItemException("MenuItem is already registered into db");
            } else
                throw new DAOException("Unexpected SQLException while updating MenuItems: " + e.getMessage());
        } finally {
            this.disconnect();
        }

        this.disconnect();

        postInsert(item, restaurant);
    }

    private void postInsert(MenuItem item, Restaurant restaurant) throws DAOException {

        item.setRecordId(getItemId(item, restaurant));

        IngredientDAO ingredientDAO = new IngredientDAOImpl();

        if(item instanceof DrinkItem drinkItem)
            for(Ingredient ingredient : drinkItem.getIngredients())
                ingredientDAO.addIngredientToMenuItemRecord(item.getRecordId(), ingredient);
    }

    @Override
    public List<MenuItem> readMenuItemsByRestaurantId(int id) throws DAOException {
        ArrayList<MenuItem> itemsList = new ArrayList<>();

        this.connect();

        String query = "SELECT name, price, availability, category, description, ingredients_string, tags," +
                "the_cocktail_db_drink_id, id, restaurant_fk " +
                "FROM menu_items LEFT JOIN non_drink_item_ingredients_string " +
                "WHERE restaurant_fk=?";

        ResultSet resultSet = null;

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            MenuItem item;

            //  An IngredientDAO to load ingredients of drink items
            IngredientDAOImpl ingredientDAO = new IngredientDAOImpl();

            while(resultSet.next()) {

                if(!resultSet.getString("the_cocktail_db_drink_id").equals("")) {
                    //  Creating a new DrinkItem
                    DrinkItem drinkItem = new DrinkItem(resultSet.getString("name"),
                            resultSet.getString(CATEGORY_COLUMN_STRING),
                            resultSet.getString(DESCRIPTION_COLUMN_STRING),
                            resultSet.getDouble(PRICE_COLUMN_STRING),
                            resultSet.getInt("the_cocktail_db_drink_id"),
                            resultSet.getInt("id"));

                    //  Reading and adding ingredients to drinkItem
                    for(Ingredient ingredient : ingredientDAO.readIngredientsByItemId(resultSet.getInt("id")))
                        drinkItem.addIngredient(ingredient);

                    //  setting item to the newly created DrinkItem
                    item = drinkItem;
                } else
                    //  Creating a new NonDrinkItem
                    item = new NonDrinkItem(resultSet.getString("name"),
                            resultSet.getString(CATEGORY_COLUMN_STRING),
                            resultSet.getString(DESCRIPTION_COLUMN_STRING),
                            resultSet.getString("ingredients_string"),
                            resultSet.getDouble(PRICE_COLUMN_STRING),
                            resultSet.getBoolean("availability"),
                            resultSet.getInt("id"));


                StringTokenizer tagsTokenizer = new StringTokenizer(resultSet.getString("tags"));
                //  Tokenizing tags and adding them to the item
                while(tagsTokenizer.hasMoreTokens())
                    item.addTag(tagsTokenizer.nextToken());

                //  Adding item to list
                itemsList.add(item);
            }
        } catch (SQLException e) {
            throw new DAOException("SQLException during menu_item parsing for restaurant with id=" + id + ".\n" +
                    "Exception message: " + e.getMessage());
        } finally {
            this.disconnect();
        }

        this.disconnect();

        return itemsList;
    }

    @Override
    public boolean refreshItem(MenuItem item) throws DAOException {
        this.connect();

        String query = "SELECT name, price, availability, description, category, " +
                "the_cocktail_db_drink_id, restaurant_fk " +
                "FROM menu_items WHERE id=?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, item.getRecordId());

            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
                return false;

            if(item.getName().equals(resultSet.getString("name")))
                item.setName(resultSet.getString("name"));

            item.setPrice(resultSet.getDouble(PRICE_COLUMN_STRING));

            if(item instanceof NonDrinkItem nonDrinkItem)
                nonDrinkItem.setAvailability(resultSet.getBoolean("availability"));

            item.setCategory(resultSet.getString(CATEGORY_COLUMN_STRING));
            item.setDescription(resultSet.getString(DESCRIPTION_COLUMN_STRING));

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            this.disconnect();
        }

        this.disconnect();

        return true;
    }

    @Override
    public int getRestaurantIdForItem(MenuItem item) throws DAOException {

        int result = -1;

        this.connect();

        String query = "SELECT restaurant_fk FROM menu_items WHERE id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, item.getRecordId());

            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
                throw new DAOItemNotFoundException("MenuItem (id:" + item.getRecordId() + ") not found while retrieving restaurant id!");

            result = resultSet.getInt("restaurant_fk");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.disconnect();

        return result;
    }

    @Override
    public void updateItem(MenuItem item, Restaurant restaurant) throws DAOException {
        this.connect();

        String query = "UPDATE menu_items SET price = ?, availability = ? , category = ?, " +
                "description = ?, tags = ?" +
                "WHERE id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, item.getPrice());
            preparedStatement.setBoolean(2, item.isAvailable());
            preparedStatement.setString(3, item.getCategory());
            preparedStatement.setString(4, item.getDescription());

            StringBuilder tagStringBuilder = new StringBuilder();
            for(int i=0; i<item.getTagCount(); i++)
                (i != 0 ? tagStringBuilder.append(", ") : tagStringBuilder).append(item.getTag(i).toLowerCase(Locale.ROOT));

            preparedStatement.setString(5, tagStringBuilder.toString());

            preparedStatement.setInt(6, item.getRecordId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Unexpected SQLException while updating MenuItems: " + e.getMessage());
        } finally {
            this.disconnect();
        }

        this.disconnect();
    }

    @Override
    public int getItemId(MenuItem menuItem, Restaurant restaurant) throws DAOException {
        this.connect();

        int id = -1;

        String query = "SELECT id FROM menu_items WHERE name=? AND restaurant_fk=?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, menuItem.getName());
            preparedStatement.setInt(2, restaurant.getRecordId());

            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
                throw new DAOItemNotFoundException("MenuItem for given restaurant not found.");

            id = resultSet.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }

        this.disconnect();

        return id;
    }
}
