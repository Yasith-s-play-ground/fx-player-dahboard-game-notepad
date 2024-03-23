package lk.ijse.dep12.fx.animal_game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setResizable(false);
        primaryStage.setTitle("Jurassic Escape: Prehistoric Pursuit");
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/GameView.fxml"))));
        primaryStage.centerOnScreen();
        primaryStage.show();

    }
}
