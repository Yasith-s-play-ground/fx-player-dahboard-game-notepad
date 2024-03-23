package lk.ijse.dep12.fx.note_pad.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainViewController {
    public AnchorPane root;

    public void mnItmAboutOnAction(ActionEvent actionEvent) throws IOException {
        Stage aboutStage = new Stage();
        aboutStage.initModality(Modality.APPLICATION_MODAL); // set modality
        //aboutStage.initModality(Modality.WINDOW_MODAL); // set modality
        //aboutStage.initOwner(root.getScene().getWindow());
        URL resource = getClass().getResource("/view/AboutWindowView.fxml");
        AnchorPane container = FXMLLoader.load(resource);
        Scene aboutScene = new Scene(container);
        aboutStage.setScene(aboutScene);
        aboutStage.centerOnScreen();
        aboutStage.show();
    }
}
