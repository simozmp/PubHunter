package view.javafx.desktop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import logic.bean.ProductRequestBean;
import logic.bean.TableServiceBean;
import view.WorkspaceViewController;

import java.net.URL;
import java.util.Objects;

public class DesktopWorkspaceViewController implements WorkspaceViewController {

    @FXML private WebView logoWebView;
    @FXML private ListView<TableServiceBean> tableServiceListView;
    @FXML private ListView<ProductRequestBean> productRequestListView;

    private ObservableList<TableServiceBean> tableServiceObservableList;
    private ObservableList<ProductRequestBean> productRequestObservableList;

    @FXML
    private void initialize() {
        WebEngine logoWebEngine;

        logoWebEngine = logoWebView.getEngine();
        URL url1 = getClass().getResource("Logo.svg");
        logoWebEngine.load(Objects.requireNonNull(url1).toExternalForm());

        tableServiceObservableList = FXCollections.observableArrayList();
        tableServiceListView.setItems(tableServiceObservableList);
        productRequestListView.setFixedCellSize(-1);
        productRequestListView.setCellFactory(listview -> new TableServiceListCell(this));
        productRequestListView.setSelectionModel(new NoSelectionModel());

        productRequestObservableList = FXCollections.observableArrayList();
        productRequestListView.setItems(productRequestObservableList);
        productRequestListView.setFixedCellSize(-1);
        productRequestListView.setCellFactory(listview -> new ProductRequestListCell(this));
        productRequestListView.setSelectionModel(new NoSelectionModel());
    }
}
