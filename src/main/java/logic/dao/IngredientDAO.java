package logic.dao;

import logic.exception.DAOException;
import logic.model.Ingredient;
import logic.model.MenuItem;
import logic.model.Restaurant;

import java.util.List;

public interface IngredientDAO extends DAO {
    void insertSupplyToRestaurant(Ingredient supply, Restaurant restaurant) throws DAOException;

    List<Ingredient> getIngredientsByRestaurant(Restaurant restaurant) throws DAOException;

    List<Ingredient> readIngredientsByItemId(int id) throws DAOException;

    boolean isIngredientAvailableAtRestaurant(Ingredient ingredient, int restaurantId) throws DAOException;

    void addIngredientToMenuItemRecord(int itemId, Ingredient ingredient) throws DAOException;
}
