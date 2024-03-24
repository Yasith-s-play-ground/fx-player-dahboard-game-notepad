package lk.ijse.dep12.fx.media_player.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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
    public Slider sldrPlay;
    private boolean isPlaying;

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

    public void btnPlayOnAction(ActionEvent actionEvent) {
        isPlaying = true;
        double startValue = sldrPlay.getValue();
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(1000),
                        event -> {
                            if (isPlaying && sldrPlay.getValue() < sldrPlay.getMax())
                                sldrPlay.setValue(sldrPlay.getValue() + 1);
                        }
                )
        );
        timeline.setCycleCount(100);
        timeline.play();
    }

    public void btnPauseOnAction(ActionEvent actionEvent) {
        isPlaying = false;
    }

    public void btnStopOnAction(ActionEvent actionEvent) {
        sldrPlay.setValue(0);
        isPlaying = false;
    }

    public void btnMinimizeOnAction(ActionEvent actionEvent) {
        ((Stage) root.getScene().getWindow()).toBack();
    }
}
