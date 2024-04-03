package lk.ijse.dep12.fx.note_pad.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;

public class MainViewController {
    public AnchorPane root;
    public MenuItem mnItmNewWindow;

    private static int untitledDocumentCount = 1;
    public MenuItem mnItemOpen;
    public MenuItem mnItemSave;
    public TextArea txtArea;
    public MenuItem mnItemSaveAs;
    //private File file = null;
    private File openedFile = null;


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

    public void mnItmNewWindowOnAction(ActionEvent actionEvent) throws IOException {
        Stage newWindowStage = new Stage();

        URL resource = getClass().getResource("/view/MainView.fxml");
        AnchorPane container = FXMLLoader.load(resource);
        Scene aboutScene = new Scene(container);
        newWindowStage.setScene(aboutScene);
        newWindowStage.setTitle("Untitled Document " + ++untitledDocumentCount);
        newWindowStage.centerOnScreen();
        newWindowStage.show();
    }

    public void mnItemOpenOnAction(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser(); // predefined class to access files
        fileChooser.setTitle("Open text files");
        // for txt
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text files", "*.txt"));

        //to select a single file
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());// as a modal window

        if (file != null) {
            loadTextToTextArea(file);
            openedFile = file;
            ((Stage) root.getScene().getWindow()).setTitle(openedFile.getName());
        } else if (openedFile == null) {
            ((Stage) root.getScene().getWindow()).setTitle("Untitled Document");
        }
    }

    private void loadTextToTextArea(File file) throws IOException {
        String text = "";
        try (FileInputStream fis = new FileInputStream(file)) {
            while (true) {
                int read = fis.read();
                if (read == -1) break;
                text += (char) read;
            }
        }
        txtArea.setText(text);
    }

    private void saveTextFile(File file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            char[] charArray = txtArea.getText().toCharArray();
            for (char c : charArray) {
                fos.write(c);
            }
        }
        openedFile = file;
        ((Stage) root.getScene().getWindow()).setTitle(openedFile.getName());
    }

    public void mnItemSaveOnAction(ActionEvent actionEvent) throws IOException {
        if (openedFile != null) {
            saveTextFile(openedFile);
        } else {
            mnItemSaveAsOnAction(new ActionEvent());
        }
    }

    public void mnItemSaveAsOnAction(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save text file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text file", "*.txt"));

        File file = fileChooser.showSaveDialog(root.getScene().getWindow());
        if (file != null) {
            saveTextFile(file);
        }
    }
}
