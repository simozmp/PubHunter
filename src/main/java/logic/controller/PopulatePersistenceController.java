package logic.controller;

import logic.dao.UserDAO;
import logic.dao.implementation.RestaurantDAOImpl;
import logic.dao.implementation.UserDAOImpl;
import logic.exception.DAOException;
import logic.exception.DAOInsertOnExistingItemException;
import logic.exception.PwdHasherException;
import logic.model.*;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * This class defines static methods to populate persistence with wired dummy data, useful for testing
 */
public class PopulatePersistenceController {

    private PopulatePersistenceController() {

    }

    /**
     * The main operation of the class. This saves into the DB data for:
     * - a new user
     * - their restaurant
     * - 4 menu items:
     *      - 2 available drink items (with their thecocktaildb.com drink id)
     *      - 1 unavailable drink item (with its thecocktaildb.com drink id)
     *      - 1 non drink item
     *
     * @return The TableService object referring to the written data
     * @throws AddressException
     * @throws PwdHasherException
     */
    public static TableService awesomeServiceData() throws AddressException, PwdHasherException {
        consolePrintln(">\tsetting up dummy data");

        ArrayList<MenuItem> menuItemList = new ArrayList<>();

        String commonStyle = "Alcoholic";
        String drinkCategoryString = "Drinks";

        DrinkItem drinkItem;

        drinkItem = new DrinkItem("Mojito", drinkCategoryString,
                "The freshest of them all!", 7,
                11000);

        drinkItem.addIngredient(new Ingredient("Rum"));
        drinkItem.addIngredient(new Ingredient("Lime"));
        drinkItem.addIngredient(new Ingredient("Sugar"));
        drinkItem.addIngredient(new Ingredient("Mint"));
        drinkItem.addIngredient(new Ingredient("Soda water"));
        drinkItem.addTag(commonStyle);
        drinkItem.addTag("Cool ;)");

        menuItemList.add(drinkItem);


        drinkItem = new DrinkItem("Gin Fizz", drinkCategoryString,
                "The cheapest drink in our menu. For real classic vibes hound!", 4,
                11410);

        drinkItem.addIngredient(new Ingredient("Gin"));
        drinkItem.addIngredient(new Ingredient("Lemon"));
        drinkItem.addIngredient(new Ingredient("Sugar"));
        drinkItem.addIngredient(new Ingredient("Carbonated Water"));

        drinkItem.addTag(commonStyle);
        drinkItem.addTag("Coolest! ;)");
        menuItemList.add(drinkItem);


        drinkItem = new DrinkItem("Long Island Tea", drinkCategoryString,
                "The longest tea drink, you should try that!", 10,
                11002);

        drinkItem.addIngredient(new Ingredient("Rum"));
        drinkItem.addIngredient(new Ingredient("Lime"));
        drinkItem.addIngredient(new Ingredient("Sugar"));
        drinkItem.addIngredient(new Ingredient("Mint"));
        drinkItem.addIngredient(new Ingredient("Soda Water"));

        drinkItem.addTag(commonStyle);
        drinkItem.addTag("SuperCOOOL!");

        menuItemList.add(drinkItem);

        NonDrinkItem nonDrinkItem = new NonDrinkItem("Grilled Steak", "Meals",
                "The biggest steak you would find with this app!",
                "Pig meal, pink himalayan salt", 14, true);

        menuItemList.add(nonDrinkItem);

        //  manager User generation
        User manager = new User("Davide", "Falessi", new InternetAddress("falessi@ing.uniroma2.it"),
                "not_an_actual_password");

        Restaurant testRestaurant = new Restaurant("Falessi cocktail bar", manager);
        consolePrintln(">\trestaurant: " + testRestaurant.getClass());

        for (MenuItem item : menuItemList) {

            testRestaurant.addMenuItem(item);

            if(item instanceof DrinkItem drinkItem1)
                for(Ingredient ingredient : drinkItem1.getIngredients())
                    testRestaurant.addSupply(ingredient);
        }

        Table restaurantTable = new Table("1A", 2);
        testRestaurant.addTable(restaurantTable);
        consolePrintln(">\trestaurantTable: " + restaurantTable.getClass());

        Table restaurantTable2 = new Table("2A", 4);
        testRestaurant.addTable(restaurantTable2);
        consolePrintln(">\trestaurantTable: " + restaurantTable.getClass());

        //  Creating a User as a "customer" for the table service
        User customer = new User("Simone", "Zomparelli", new InternetAddress("simo.zmp96@gmail.com"),
                "not_my_actual_pwd");

        //  manager User writing into db
        UserDAO userDAO = new UserDAOImpl();
        try {
            userDAO.insert(manager);
            userDAO.insert(customer);
        } catch (DAOInsertOnExistingItemException e) {
            consolePrintln("User data already written.");
        } catch (DAOException e) {
            e.printStackTrace();
        }

        //  testRestaurant writing into db
        RestaurantDAOImpl restaurantDAO = new RestaurantDAOImpl();
        try {
            restaurantDAO.insert(testRestaurant);
        } catch (DAOInsertOnExistingItemException e) {
            consolePrintln("Restaurant data already written.");
        } catch (DAOException e) {
            e.printStackTrace();
        }

        TableService service = new TableService(restaurantTable, customer, LocalDate.now(), LocalTime.now());
        consolePrintln(">\tservice: " + service.getClass());

        return service;
    }

    private static void consolePrintln(String s) {
        System.out.println(s);
    }
}
