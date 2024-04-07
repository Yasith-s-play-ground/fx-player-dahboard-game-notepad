package lk.ijse.dep12.fx.note_pad;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dep12.fx.note_pad.controller.MainViewController;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Untitled Document 1");
        URL resource = getClass().getResource("/view/MainView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        AnchorPane container = fxmlLoader.load();
        primaryStage.setScene(new Scene(container));
        MainViewController controller = fxmlLoader.getController(); //getting the controller of main view

        controller.window = primaryStage; // assigning the created stage to window variable in Main Controller
        primaryStage.show();
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(event -> {
            if (controller.getWindowTitle().startsWith("*")) { // if there is unsaved data
                event.consume();
                controller.displayClosingAlert(); // display closing alert to the user
            }
        });
    }
}
