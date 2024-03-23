package lk.ijse.dep12.fx.dashboard.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;

public class MainViewController {
    public AnchorPane root;
    public Button btnDashboard;
    public Button btnManageCustomer;
    public Button btnManageStock;
    public Button btnPlaceOrder;
    public Button btnViewReports;
    public Button btnLogout;
    public AnchorPane windowDisplayPane;

    public void initialize() {

//        Platform.setImplicitExit(false);
//        root.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent event) {
//                event.consume();
//            }
//        });

    }

    public void btnDashboardOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane container = FXMLLoader.load(getClass().getResource("/view/DashboardView.fxml"));
        windowDisplayPane.getChildren().clear();
        windowDisplayPane.getChildren().add(container);
        AnchorPane.setLeftAnchor(container, 0.0);
        AnchorPane.setRightAnchor(container, 0.0);
        AnchorPane.setTopAnchor(container, 0.0);
        AnchorPane.setBottomAnchor(container, 0.0);
    }

    public void btnManageCustomerOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane container = FXMLLoader.load(getClass().getResource("/view/ManageCustomerView.fxml"));
        windowDisplayPane.getChildren().clear();
        windowDisplayPane.getChildren().add(container);
        AnchorPane.setLeftAnchor(container, 0.0);
        AnchorPane.setRightAnchor(container, 0.0);
        AnchorPane.setTopAnchor(container, 0.0);
        AnchorPane.setBottomAnchor(container, 0.0);
    }

    public void btnManageStockOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane container = FXMLLoader.load(getClass().getResource("/view/ManageStockView.fxml"));
        windowDisplayPane.getChildren().clear();
        windowDisplayPane.getChildren().add(container);
        AnchorPane.setLeftAnchor(container, 0.0);
        AnchorPane.setRightAnchor(container, 0.0);
        AnchorPane.setTopAnchor(container, 0.0);
        AnchorPane.setBottomAnchor(container, 0.0);

    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane container = FXMLLoader.load(getClass().getResource("/view/PlaceOrderView.fxml"));
        windowDisplayPane.getChildren().clear();
        windowDisplayPane.getChildren().add(container);
        AnchorPane.setLeftAnchor(container, 0.0);
        AnchorPane.setRightAnchor(container, 0.0);
        AnchorPane.setTopAnchor(container, 0.0);
        AnchorPane.setBottomAnchor(container, 0.0);
    }

    public void btnViewReportsOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane container = FXMLLoader.load(getClass().getResource("/view/ViewReportsView.fxml"));
        windowDisplayPane.getChildren().clear();
        windowDisplayPane.getChildren().add(container);
        AnchorPane.setLeftAnchor(container, 0.0);
        AnchorPane.setRightAnchor(container, 0.0);
        AnchorPane.setTopAnchor(container, 0.0);
        AnchorPane.setBottomAnchor(container, 0.0);
    }

    public void btnLogoutOnAction(ActionEvent actionEvent) throws IOException {
        Stage loginStage = new Stage();
        loginStage.setResizable(false);
        loginStage.centerOnScreen();
        loginStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"))));
        loginStage.show();

        ((Stage) root.getScene().getWindow()).close();
    }
}
