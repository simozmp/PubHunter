package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class OrderView extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene;
        Parent viewParent = null;

        try {
            viewParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("order-view.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        scene = new Scene(Objects.requireNonNull(viewParent));

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
