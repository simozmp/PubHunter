package view.javafx.desktop;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.controller.OrderController;
import logic.controller.PopulatePersistenceController;
import logic.exception.PwdHasherException;
import logic.model.*;

import javax.mail.internet.AddressException;
import java.time.LocalDate;
import java.time.LocalTime;

public class MainDemoApplicationViewController {

    @FXML AnchorPane root;
    @FXML TextArea consoleTextArea;

    @FXML
    private void demoOrderUC() throws AddressException, PwdHasherException {
        Stage demoStage = (Stage) this.root.getScene().getWindow();

        consolePrintln("Order use case preparing...");

        TableService service = PopulatePersistenceController.awesomeServiceData();

        consolePrintln("Opening OrderView in new stage, and binding the use case controller");

        DesktopOrderApplication desktopOrderApplication = new DesktopOrderApplication();

        new OrderController(desktopOrderApplication.getController(), service);

        demoStage.close();
    }

    @FXML
    private void demoWorkspace() {
        Stage demoStage = (Stage) this.root.getScene().getWindow();

        DesktopWorkspaceApplication desktopWorkspaceApplication = new DesktopWorkspaceApplication();

        demoStage.close();
    }

    private void consolePrintln(String message) {
        String currentText = consoleTextArea.getText();
        consoleTextArea.setText(currentText + "[" + LocalDate.now() + " " + LocalTime.now() + "] " + message + "\n");
    }
}
