package view.javafx.mobile;

import javafx.stage.Stage;
import view.OrderApplication;

public class MobileOrderApplication extends OrderApplication {

    public MobileOrderApplication() {
        start(new Stage());
    }

    @Override
    public void start(Stage primaryStage) {
        startWithFXMLAndCss(primaryStage, "mobile-order-view.fxml", "mobile-application.css");
    }
}
