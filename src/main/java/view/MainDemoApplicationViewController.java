package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class MainDemoApplicationViewController {
    OrderView orderView;

    @FXML AnchorPane root;
    @FXML TextArea consoleTextArea;

    @FXML
    private void demoOrderUC() {
        consolePrintln("Order use case preparing...");

        Booking booking = awesomeBookingData();

        consolePrintln("Opening OrderView in new stage, and binding the use case controller");

        orderView = new OrderView();
        orderView.start(new Stage());

        new OrderController(orderView.getController(), booking);
    }

    private Booking awesomeBookingData() {
        consolePrintln(">\tsetting up dummy data");

        Customer customer = new UnregisteredCustomer();
        consolePrintln(">\tcustomer: " + customer.getClass());

        ArrayList<MenuItem> menuItemList = new ArrayList<>();

        String commonStyle = "Alcoholic";

        ArrayList<String> tags1 = new ArrayList<>();
        tags1.add(commonStyle);
        tags1.add("Cool ;)");
        menuItemList.add(new Drink("Mojito", "Drinks", "Rum, lime, sugar, mint, soda water", 7, true, tags1));

        ArrayList<String> tags2 = new ArrayList<>();
        tags2.add(commonStyle);
        tags2.add("Cooler ;)");
        menuItemList.add(new Drink("Gin Fizz", "Drinks", "Gin, lemon, sugar, carbonated water", 4, true, tags2));

        ArrayList<String> tags3 = new ArrayList<>();
        tags3.add(commonStyle);
        tags3.add("Coolest ;)");
        menuItemList.add(new Drink("Long Island Tea", "Drinks", "Rum, lime, sugar, mint, soda water", 10, true, tags3));

        ArrayList<String> tags4 = new ArrayList<>();
        tags4.add("!"+commonStyle);
        tags4.add("SuperCOOOL!");
        menuItemList.add(new MenuItem( "Generic meal", "Meals", "Proteins and other stuff", 14, true, tags4));

        Restaurant testRestaurant = new Restaurant(new Menu(menuItemList));
        consolePrintln(">\trestaurant: " + testRestaurant.getClass());

        Table restaurantTable = new Table("1A", testRestaurant);
        testRestaurant.addTable(restaurantTable);
        consolePrintln(">\trestaurantTable: " + restaurantTable.getClass());

        Booking booking = new Booking(customer, restaurantTable, LocalDate.now(), LocalTime.now());
        consolePrintln(">\tbooking: " + booking.getClass());

        BookingService service = new BookingService(booking);
        consolePrintln(">\tservice: " + service.getClass());

        return booking;
    }

    private void consolePrintln(String message) {
        String currentText = consoleTextArea.getText();
        consoleTextArea.setText(currentText + "[" + LocalDate.now() + " " + LocalTime.now() + "] " + message + "\n");
    }
}
