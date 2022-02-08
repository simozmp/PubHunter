package view.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class JFXMLApplication extends Application {

    protected FXMLLoader fxmlLoader;

    protected static void main(String[] args) {
        launch(args);
    }

    protected void startWithFXMLAndCss(Stage primaryStage, String fxmlFilename, String stylesheetFilename) {
        Scene scene;
        Parent viewParent = null;

        fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(fxmlFilename)));

        try {
            viewParent = fxmlLoader.load();
        } catch (IOException e) {
            Logger.getLogger("JFXMLApplication").log(Level.WARNING, "Couldn''t find fxml view file.", e);
        }

        scene = new Scene(Objects.requireNonNull(viewParent));

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(stylesheetFilename)).toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
