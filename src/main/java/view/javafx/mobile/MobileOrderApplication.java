package view.javafx.mobile;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.OrderViewController;

import java.io.IOException;
import java.util.Objects;

public class MobileOrderApplication extends Application {

    private MobileOrderViewController controller;

    public MobileOrderApplication() {
        start(new Stage());
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene;
        Parent viewParent = null;
        FXMLLoader fxmlLoader;

        fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("mobile-order-view.fxml")));

        try {
            viewParent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        controller = Objects.requireNonNull(fxmlLoader.getController());

        scene = new Scene(Objects.requireNonNull(viewParent));

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("mobile-application.css")).toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public OrderViewController getController() {
        return this.controller;
    }
}
