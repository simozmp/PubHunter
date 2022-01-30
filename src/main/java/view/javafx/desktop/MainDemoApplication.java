package view.javafx.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainDemoApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene;
        Parent viewParent = null;

        try {
            viewParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main-test-application-view.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        scene = new Scene(Objects.requireNonNull(viewParent));

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());

        stage.setScene(scene);
        stage.show();
    }
}
