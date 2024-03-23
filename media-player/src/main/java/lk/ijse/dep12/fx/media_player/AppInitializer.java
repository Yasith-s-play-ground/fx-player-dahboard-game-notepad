package lk.ijse.dep12.fx.media_player;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        AnchorPane container = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
        Scene scene = new Scene(container);
        container.setBackground(Background.EMPTY); // set container background empty
        scene.setFill(Color.TRANSPARENT); // set fill to transparent
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT); // set style to transparent
        primaryStage.centerOnScreen();
        primaryStage.show();

        container.setOnMousePressed(mousePressEvent -> {
            final double xDiff = mousePressEvent.getScreenX() - primaryStage.getX(); // get x difference of mouse pressed point and stage
            final double yDiff = mousePressEvent.getScreenY() - primaryStage.getY(); // get y difference of mouse pressed point and stage
            container.setOnMouseDragged(mouseDragEvent -> {
                primaryStage.setX(mouseDragEvent.getScreenX() - xDiff);
                primaryStage.setY(mouseDragEvent.getScreenY() - yDiff);
            });
        });
    }
}
