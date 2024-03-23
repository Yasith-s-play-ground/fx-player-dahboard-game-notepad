package lk.ijse.dep12.fx.media_player.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MainViewController {
    public AnchorPane root;
    public Button btnPlay;
    public Button btnPause;
    public Button btnStop;
    public Button btnVolumeUp;
    public Button btnVolumeDown;
    public Label lblTime;
    public Button btnMinimize;
    public Button btnMaximize;
    public Button btnClose;

    public void initialize() {

        //Display time in label

        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        lblTime.setText(timeFormat.format(System.currentTimeMillis()));
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(1000),
                        event -> {
                            //set system time to label
                            lblTime.setText(timeFormat.format(System.currentTimeMillis()));
                        }
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void btnCloseOnAction(ActionEvent actionEvent) {
        ((Stage) root.getScene().getWindow()).close();
    }
}
