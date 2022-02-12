package logic.model;

import logic.exception.PwdHasherException;
import org.junit.jupiter.api.Test;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A test class for Restaurant.
 *
 * @author simozmp
 */
class RestaurantTest {

    private static final String TEST_RESTAURANT_NAME = "TestRestaurant";
    private static final int TEST_RESTAURANT_RECORD_ID = 12;

    private static final String TEST_FIRST_NAME = "Mister";
    private static final String TEST_LAST_NAME = "Test";
    private static final String TEST_EMAIL_ADDRESS = "mister.test@testhost.com";
    private static final String TEST_PASSWORD = "TestPassw0rd";
    private static final String TEST_DRINK_NAME = "test_drink";
    private static final String TEST_DRINK_CATEGORY = "test_category";
    private static final String TEST_DRINK_DESCRIPTION = "test_description";
    private static final String TEST_TABLE_NAME = "A";

    private static final String TEST_INGREDIENT_NAME = "test_ingredient";

    Restaurant getTestRestaurant() throws AddressException, PwdHasherException {
        User testManager = new User(TEST_FIRST_NAME, TEST_LAST_NAME, new InternetAddress(TEST_EMAIL_ADDRESS), TEST_PASSWORD);
        return new Restaurant(TEST_RESTAURANT_NAME, testManager, TEST_RESTAURANT_RECORD_ID);
    }

    @Test
    void getManagerEmailString() throws AddressException, PwdHasherException {
        assertTrue(getTestRestaurant().getManagerEmailString().matches(TEST_EMAIL_ADDRESS));
    }

    @Test
    void getName() throws AddressException, PwdHasherException {
        assertTrue(getTestRestaurant().getName().matches(TEST_RESTAURANT_NAME));
    }

    @Test
    void getMenuItem() throws AddressException, PwdHasherException {
        Restaurant testRestaurant = getTestRestaurant();

        MenuItem testDrinkItem = new DrinkItem(TEST_DRINK_NAME, TEST_DRINK_CATEGORY, TEST_DRINK_DESCRIPTION, 5, 100);

        testRestaurant.addMenuItem(testDrinkItem);

        assertEquals(testDrinkItem, testRestaurant.getMenuItem(0));
    }

    @Test
    void getMenuSize() throws AddressException, PwdHasherException {
        Restaurant testRestaurant = getTestRestaurant();

        MenuItem testDrinkItem = new DrinkItem(TEST_DRINK_NAME, TEST_DRINK_CATEGORY, TEST_DRINK_DESCRIPTION, 5, 100);

        testRestaurant.addMenuItem(testDrinkItem);

        assertEquals(1, testRestaurant.getMenuSize());
    }

    @Test
    void addMenuItem() throws AddressException, PwdHasherException {
        this.getMenuItem();
    }

    @Test
    void addSupply() throws AddressException, PwdHasherException {
        this.getSupply();
    }

    @Test
    void getSuppliesCount() throws AddressException, PwdHasherException {
        int count = 5;

        Restaurant testRestaurant = this.getTestRestaurant();

        for(int i=0; i<count; i++)
            testRestaurant.addSupply(new Ingredient(TEST_INGREDIENT_NAME + "_" + i));

        assertEquals(count, testRestaurant.getSuppliesCount());
    }

    @Test
    void getSupply() throws AddressException, PwdHasherException {
        Ingredient testIngredient = new Ingredient(TEST_INGREDIENT_NAME);

        Restaurant testRestaurant = this.getTestRestaurant();

        testRestaurant.addSupply(testIngredient);

        assertEquals(testIngredient, testRestaurant.getSupply(0));
    }

    @Test
    void addTable() throws AddressException, PwdHasherException {
        this.getTable();
    }

    @Test
    void getTable() throws AddressException, PwdHasherException {
        Restaurant testRestaurant = this.getTestRestaurant();

        Table testTable = new Table(TEST_TABLE_NAME, 4);

        testRestaurant.addTable(testTable);

        assertEquals(testTable, testRestaurant.getTable(0));
    }

    @Test
    void getTablesCount() throws AddressException, PwdHasherException {
        int count = 5;

        Restaurant testRestaurant = this.getTestRestaurant();

        for(int i=0; i<count; i++)
            testRestaurant.addTable(new Table(TEST_TABLE_NAME + i, 4));

        assertEquals(count, testRestaurant.getTablesCount());
    }

    @Test
    void getRecordId() throws AddressException, PwdHasherException {
        Restaurant testRestaurant = this.getTestRestaurant();

        int testId = 15;

        testRestaurant.setRecordId(testId);

        assertEquals(testId, testRestaurant.getRecordId());
    }

    @Test
    void setRecordId() throws AddressException, PwdHasherException {
        this.getRecordId();
    }
}