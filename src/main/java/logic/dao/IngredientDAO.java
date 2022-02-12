package logic.dao;

import logic.exception.DAOException;
import logic.model.Ingredient;
import logic.model.Restaurant;

import java.util.List;

/**
 * Data access object class for Ingredient entity class.
 */
public interface IngredientDAO extends JDBCDataAccessObject {

    /**
     * Insertion operation.
     *
     * @param supply the ingredient to be written as a supply
     * @param restaurant the restaurant with given supply
     * @throws DAOException if error occurs. Details in exception message
     */
    void insertSupplyToRestaurant(Ingredient supply, Restaurant restaurant) throws DAOException;

    /**
     * Reading operation.
     *
     * @param restaurant the restaurant to search supplies for
     * @throws DAOException if error occurs. Details in exception message
     */
    List<Ingredient> getIngredientsByRestaurant(Restaurant restaurant) throws DAOException;

    /**
     * Reading operation.
     *
     * @param id the MenuItem id to search ingredients for
     * @throws DAOException if error occurs. Details in exception message
     */
    List<Ingredient> readIngredientsByItemId(int id) throws DAOException;

    /**
     * Verifies in persistence if an ingredient is in a given restaurant's supplies
     *
     * @param ingredient the ingredient to verify availability
     * @param restaurantId the restaurant to search supply for
     * @return true if ingredient is registered as a supply for restaurant, false otherwise
     * @throws DAOException if error occurs. Details in exception message
     */
    boolean isIngredientAvailableAtRestaurant(Ingredient ingredient, int restaurantId) throws DAOException;

    /**
     * Insertion operation.
     *
     * @param itemId id of the item for which ingredient will be added
     * @param ingredient the ingredient to be added
     * @throws DAOException if error occurs. Details in exception message
     */
    void addIngredientToMenuItemRecord(int itemId, Ingredient ingredient) throws DAOException;
}
