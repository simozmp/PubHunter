package view.javafx.desktop;

import javafx.stage.Stage;
import view.OrderApplication;

public class DesktopOrderApplication extends OrderApplication {

    public DesktopOrderApplication() {
        start(new Stage());
    }

    @Override
    public void start(Stage primaryStage) {
        startWithFXMLAndCss(primaryStage, "order-view.fxml", "application.css");
    }
}
