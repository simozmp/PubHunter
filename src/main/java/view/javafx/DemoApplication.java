package view.javafx;

import javafx.stage.Stage;

public class DemoApplication extends JFXMLApplication {

    @Override
    public void start(Stage stage) {
        startWithFXMLAndCss(stage, "main-test-application-view.fxml", "application.css");
    }
}
