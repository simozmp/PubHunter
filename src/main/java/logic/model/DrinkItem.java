package logic.model;

import logic.dao.implementation.IngredientDAOImpl;
import logic.dao.implementation.MenuItemDAOImpl;
import logic.exception.DAOException;

import java.util.ArrayList;
import java.util.List;

public class DrinkItem extends MenuItem {

    private final List<Ingredient> ingredients;
    int theCocktailDBId;

    public DrinkItem(String name, String category, String description, double price,
                     int theCocktailDBId) {
        super(name, category, description, price, -1);
        this.ingredients = new ArrayList<>();
        this.theCocktailDBId = theCocktailDBId;
    }

    public DrinkItem(String name, String category, String description, double price,
                     int theCocktailDBId, int persistenceId) {
        super(name, category, description, price, persistenceId);
        this.ingredients = new ArrayList<>();
        this.theCocktailDBId = theCocktailDBId;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public int getTheCocktailDBId() {
        return theCocktailDBId;
    }

    @Override
    public boolean isAvailable() {
        boolean persistenceAvailable = true;

        try {
            IngredientDAOImpl ingredientDAO = new IngredientDAOImpl();
            MenuItemDAOImpl menuItemDAO = new MenuItemDAOImpl();

            int restaurantId = menuItemDAO.getRestaurantIdForItem(this);

            for (Ingredient ingredient : this.ingredients)
                persistenceAvailable = persistenceAvailable &&
                        ingredientDAO.isIngredientAvailableAtRestaurant(ingredient, restaurantId);

        } catch (DAOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return persistenceAvailable;
    }

    @Override
    public String getIngredientsString() {
        StringBuilder result = new StringBuilder();

        for(Ingredient ingredient : ingredients)
            result.append(ingredient.getDescription()).append(", ");

        return result.substring(0, result.length()-2).concat(".");
    }

    /**
     * @return a List of Ingredients for the drink
     */
    public List<Ingredient> getIngredients() {

        List<Ingredient> ingredientsList = new ArrayList<>();

        for(Ingredient ingredient : ingredients)
            ingredientsList.add(new Ingredient(ingredient.getDescription()));

        return ingredientsList;
    }
}
