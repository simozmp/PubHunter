package view.javafx.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.WorkspaceViewController;
import view.javafx.desktop.ResourceAccessObject;

import java.io.IOException;
import java.util.Objects;

public class DesktopWorkspaceApplication extends Application {

    private WorkspaceViewController controller;

    public DesktopWorkspaceApplication() {
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

        fxmlLoader = new FXMLLoader(Objects.requireNonNull(
                ResourceAccessObject.class.getResource("workspace-view.fxml")));

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

    public WorkspaceViewController getController() {
        return controller;
    }
}
