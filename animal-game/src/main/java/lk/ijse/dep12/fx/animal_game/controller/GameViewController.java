package lk.ijse.dep12.fx.animal_game.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class GameViewController {
    public AnchorPane root;
    public Label lblBoy;
    public Label lblDinasour;
    public Label lblGameOver;
    public Label lblGameWon;
    private boolean gameStopped;

    private void displayGameWonOrLost() {
        if (!gameStopped && lblBoy.getLayoutX() >= root.getPrefWidth()) {
            lblGameWon.setVisible(true);
            gameStopped = true;
        } else if (((lblDinasour.getLayoutX() <= lblBoy.getLayoutX() + 75) && (lblDinasour.getLayoutX() >= lblBoy.getLayoutX())) && (lblBoy.getLayoutY() + 120 >= lblDinasour.getLayoutY())) {
            lblGameOver.setVisible(true);
            gameStopped = true;
        }
    }

    public void initialize() {
        double boyStartY = lblBoy.getLayoutY();

        lblGameOver.setVisible(false);
        lblGameWon.setVisible(false);
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(100),
                        event -> {
                            if (!gameStopped) {
                                if (lblDinasour.getLayoutX() <= 0) lblDinasour.setLayoutX(root.getPrefWidth());

                                else {
                                    lblDinasour.setLayoutX(lblDinasour.getLayoutX() - 5);
                                    displayGameWonOrLost();
                                }
                            }
                        }
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        Platform.runLater(() ->
        {

            root.getScene()
                    .setOnKeyPressed(keyEvent -> {
                        if (keyEvent.getCode() == KeyCode.SPACE) {

                            final Timeline spaceKeyPressedTimeline = new Timeline(
                                    new KeyFrame(
                                            Duration.millis(200),
                                            event -> {
                                                if (!gameStopped && lblBoy.getLayoutY() == boyStartY)
                                                    lblBoy.setLayoutY(lblBoy.getLayoutY() - 130);
                                            }
                                    )
                            );
                            spaceKeyPressedTimeline.setCycleCount(1);
                            spaceKeyPressedTimeline.play();
                        } else if (!gameStopped && keyEvent.getCode() == KeyCode.RIGHT) {
                            lblBoy.setLayoutX(lblBoy.getLayoutX() + 10);
                        } else if (!gameStopped && keyEvent.getCode() == KeyCode.LEFT) {
                            if (!(lblBoy.getLayoutX() <= 0)) lblBoy.setLayoutX(lblBoy.getLayoutX() - 10);
                        }
                    });
            root.getScene().setOnKeyReleased(keyEvent -> {
                if (!gameStopped && keyEvent.getCode() == KeyCode.SPACE) {
                    final Timeline spaceKeyReleasedTimeline = new Timeline(
                            new KeyFrame(
                                    Duration.millis(200),
                                    event -> {
                                        lblBoy.setLayoutY(boyStartY);
                                        lblBoy.setLayoutX(lblBoy.getLayoutX() + 100);
                                    }
                            )
                    );
                    spaceKeyReleasedTimeline.setCycleCount(1);
                    spaceKeyReleasedTimeline.play();
                }
            });
        });
    }
}
