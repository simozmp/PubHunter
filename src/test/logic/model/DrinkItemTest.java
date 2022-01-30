package logic.model;

import logic.dao.implementation.RestaurantDAOImpl;
import logic.exception.DAOException;
import logic.exception.PwdHasherException;
import org.junit.jupiter.api.Test;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import static org.junit.jupiter.api.Assertions.*;

class DrinkItemTest {

    @Test
    String getIngredientsString() {

        DrinkItem testItem = new DrinkItem("Gin Tonic", "Soft drinks",
                "The best on the catalog", 7.00, 10);

        testItem.addIngredient(new Ingredient("Gin"));
        testItem.addIngredient(new Ingredient("Tonic water"));

        assertEquals("Gin, Tonic water.", testItem.getIngredientsString());

        return testItem.getIngredientsString();
    }

    @Test
    void isAvailable() throws AddressException, PwdHasherException, DAOException {

        RestaurantDAOImpl restaurantDAO = new RestaurantDAOImpl();
        Restaurant testRestaurant = new Restaurant("Testaurant", new User("pippo", "franco", new InternetAddress("pippo@franco.com"),
                "testPwd"));

        testRestaurant.addSupply(new Ingredient("Gin"));
        testRestaurant.addSupply(new Ingredient("Tonic water"));

        DrinkItem testItem = new DrinkItem("Gin Tonic", "Soft drinks",
                "The best on the catalog", 7.00, 10);

        testItem.addIngredient(new Ingredient("Gin"));
        testItem.addIngredient(new Ingredient("Tonic water"));

        testRestaurant.addMenuItem(testItem);

        restaurantDAO.insert(testRestaurant);

    }
}