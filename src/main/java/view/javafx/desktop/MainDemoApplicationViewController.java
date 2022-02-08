package view.javafx.desktop;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.controller.OrderController;
import logic.dao.UserDAO;
import logic.dao.implementation.RestaurantDAOImpl;
import logic.dao.implementation.UserDAOImpl;
import logic.exception.DAOException;
import logic.exception.DAOInsertOnExistingItemException;
import logic.exception.EnvironmentLoadException;
import logic.exception.PwdHasherException;
import logic.model.*;
import view.OrderApplication;
import view.javafx.mobile.MobileOrderApplication;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

public class MainDemoApplicationViewController {

    @FXML AnchorPane root;
    @FXML TextArea consoleTextArea;

    @FXML
    private void demoMobileOrderUC() throws AddressException, PwdHasherException, EnvironmentLoadException {
        polymorphDemoUC("MOBILE");
    }

    @FXML
    private void demoDesktopOrderUC() throws AddressException, PwdHasherException, EnvironmentLoadException {
        polymorphDemoUC("DESKTOP");
    }

    private void polymorphDemoUC(String environment) throws EnvironmentLoadException, AddressException, PwdHasherException {
        Stage demoStage = (Stage) this.root.getScene().getWindow();

        consolePrintln("Populating db...");

        TableService service = awesomeServiceData();

        consolePrintln("...done!");

        consolePrintln("Instantiating orderApplication...");

        OrderApplication orderApplication = null;

        switch (environment) {
            case "DESKTOP":
                orderApplication = new DesktopOrderApplication();
                break;
            case "MOBILE":
                orderApplication = new MobileOrderApplication();
                break;
            default:
                throw new EnvironmentLoadException("Environment string unrecognized.");
        }

        consolePrintln("...done!");

        consolePrintln("Starting use case with orderApplication.");

        new OrderController(Objects.requireNonNull(orderApplication.getController()), service);

        demoStage.close();
    }

    private void consolePrintln(String message) {
        String currentText = consoleTextArea.getText();
        consoleTextArea.setText(currentText + "[" + LocalDate.now() + " " + LocalTime.now() + "] " + message + "\n");
    }


    /**
     * This method saves into the DB data for:
     * - a new user
     * - their restaurant
     * - 4 menu items:
     *      - 2 available drink items (with their thecocktaildb.com drink id)
     *      - 1 unavailable drink item (with its thecocktaildb.com drink id)
     *      - 1 non drink item
     *
     * @return The TableService object referring to the written data
     * @throws PwdHasherException if environment does not provide utils for hashing password
     */
    public static TableService awesomeServiceData() throws AddressException, PwdHasherException {

        ArrayList<MenuItem> menuItemList = new ArrayList<>();

        String alcoholicString = "Alcoholic";
        String sugarString = "Sugar";
        String sodaWaterString = "Soda Water";
        String rumString = "Rum";
        String drinkCategoryString = "Drinks";

        DrinkItem drinkItem;

        drinkItem = new DrinkItem("Mojito", drinkCategoryString,
                "The freshest of them all!", 7,
                11000);

        drinkItem.addIngredient(new Ingredient("Rum"));
        drinkItem.addIngredient(new Ingredient("Lime"));
        drinkItem.addIngredient(new Ingredient(sugarString));
        drinkItem.addIngredient(new Ingredient("Mint"));
        drinkItem.addIngredient(new Ingredient(sodaWaterString));
        drinkItem.addTag(alcoholicString);
        drinkItem.addTag("Cool ;)");

        menuItemList.add(drinkItem);


        drinkItem = new DrinkItem("Gin Fizz", drinkCategoryString,
                "The cheapest drink in our menu. For real classic vibes hound!", 4,
                11410);

        drinkItem.addIngredient(new Ingredient("Gin"));
        drinkItem.addIngredient(new Ingredient("Lemon"));
        drinkItem.addIngredient(new Ingredient(sugarString));
        drinkItem.addIngredient(new Ingredient("Carbonated Water"));

        drinkItem.addTag(alcoholicString);
        drinkItem.addTag("Coolest! ;)");
        menuItemList.add(drinkItem);

        drinkItem = new DrinkItem("Long Island Tea", drinkCategoryString,
                "The longest tea drink, you should try that!", 10,
                11002);

        drinkItem.addIngredient(new Ingredient(rumString));
        drinkItem.addIngredient(new Ingredient("Lime"));
        drinkItem.addIngredient(new Ingredient(sugarString));
        drinkItem.addIngredient(new Ingredient("Mint"));
        drinkItem.addIngredient(new Ingredient(sodaWaterString));

        drinkItem.addTag(alcoholicString);
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

        for (MenuItem item : menuItemList) {

            testRestaurant.addMenuItem(item);

            if(item instanceof DrinkItem drinkItem1)
                for(Ingredient ingredient : drinkItem1.getIngredients())
                    testRestaurant.addSupply(ingredient);
        }

        Table restaurantTable = new Table("1A", 2);
        testRestaurant.addTable(restaurantTable);

        Table restaurantTable2 = new Table("2A", 4);
        testRestaurant.addTable(restaurantTable2);

        //  Creating a User as a "customer" for the table service
        User customer = new User("Simone", "Zomparelli", new InternetAddress("simo.zmp96@gmail.com"),
                "not_my_actual_pwd");

        //  manager User writing into db
        UserDAO userDAO = new UserDAOImpl();
        try {
            userDAO.insert(manager);
            userDAO.insert(customer);
        } catch (DAOInsertOnExistingItemException ignored) {
            //  ignored as this would mean that is not the first time this methods run on the DAO database
        } catch (DAOException e) {
            e.printStackTrace();
        }

        //  testRestaurant writing into db
        RestaurantDAOImpl restaurantDAO = new RestaurantDAOImpl();
        try {
            restaurantDAO.insert(testRestaurant);
        } catch (DAOInsertOnExistingItemException ignored) {
            //  ignored as this would mean that is not the first time this methods run on the DAO database
        } catch (DAOException e) {
            e.printStackTrace();
        }

        return new TableService(restaurantTable, customer, LocalDate.now(), LocalTime.now());
    }
}
