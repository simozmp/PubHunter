package view.javafx.desktop;

import javafx.stage.Stage;
import view.javafx.JFXMLApplication;

public class MainDemoApplication extends JFXMLApplication {

    @Override
    public void start(Stage stage) {
        startWithFXMLAndCss(stage, "main-test-application-view.fxml", "application.css");
    }
}
