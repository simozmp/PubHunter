package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.OrderController;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OrderView extends Application {

    private OrderViewController controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene;
        Parent viewParent = null;
        FXMLLoader fxmlLoader;

        fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("order-view.fxml")));

        try {
            viewParent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        controller = (OrderViewController) Objects.requireNonNull(fxmlLoader.getController());

        scene = new Scene(Objects.requireNonNull(viewParent));

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public OrderViewController getController() {
        return controller;
    }
}
