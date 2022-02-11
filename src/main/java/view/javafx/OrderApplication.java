package view.javafx;

import javafx.stage.Stage;
import view.OrderViewController;
import view.javafx.JFXMLApplication;

public abstract class OrderApplication extends JFXMLApplication {

    protected OrderViewController controller;

    @Override
    protected void startWithFXMLAndCss(Stage primaryStage, String fxmlFilename, String stylesheetFilename) {
        super.startWithFXMLAndCss(primaryStage, fxmlFilename, stylesheetFilename);

        this.controller = fxmlLoader.getController();
    }

    public OrderViewController getController() {
        return controller;
    }
}
