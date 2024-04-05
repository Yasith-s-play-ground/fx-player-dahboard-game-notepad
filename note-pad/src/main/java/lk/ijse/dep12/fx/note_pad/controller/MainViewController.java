package lk.ijse.dep12.fx.note_pad.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Optional;

public class MainViewController {
    public AnchorPane root;
    public MenuItem mnItmNewWindow;

    private static int untitledDocumentCount = 1;
    public MenuItem mnItemOpen;
    public MenuItem mnItemSave;
    public TextArea txtArea;
    public MenuItem mnItemSaveAs;
    public MenuItem mnItemNew;
    //private File file = null;
    private File openedFile = null;
    //String originalText = null;

    private Stage window;

//    public void setWindow(Stage window) {
//        this.window = window;
//    }

    public String getWindowTitle() {
        return window.getTitle();
    }

    public void initialize() {
        //window = (Stage) root.getScene().getWindow();
        //to implement closing action

    }

    public void displayClosingAlert() {
        ButtonType btnSave;
        btnSave = openedFile != null ? new ButtonType("Save") : new ButtonType("Save As");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "There is unsaved data on file, Do you want to close without saving ?", ButtonType.YES, ButtonType.CANCEL, btnSave);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            window.close();
        } else if (buttonType.get() == btnSave) {
            try {
                mnItemSaveOnAction(new ActionEvent());
                window.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

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

    private void setTitle(String title) {
        ((Stage) root.getScene().getWindow()).setTitle(title);
    }

    public void mnItemOpenOnAction(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser(); // predefined class to access files
        fileChooser.setTitle("Open text files");
        //set initial directory for file chooser
        fileChooser.setInitialDirectory(new File(System.getenv("HOME"), "Desktop"));
        // for txt
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text files", "*.txt"));

        //to select a single file
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());// as a modal window

        if (file != null) {
            if (openedFile != null && !openedFile.equals(file)) { // if already opened a file here
                Stage newStage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
                AnchorPane container = fxmlLoader.load();
                newStage.setScene(new Scene(container));
                newStage.show();
                MainViewController controller = fxmlLoader.getController();
                System.out.println(controller);

                if (controller != null) controller.loadTextToTextArea(file);
//                String text = "";
//                try (FileInputStream fis = new FileInputStream(file)) {
//                    while (true) {
//                        int read = fis.read();
//                        if (read == -1) break;
//                        text += (char) read;
//                    }
//                }
//
//                txtArea.setText(text);

            } else {
                loadTextToTextArea(file);
                openedFile = file;
                setTitle(openedFile.getName());
            }
            //((Stage) root.getScene().getWindow()).setTitle(openedFile.getName());
        } else if (openedFile == null) {
            setTitle("Untitled Document");
            //((Stage) root.getScene().getWindow()).setTitle("Untitled Document 1");
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
        // originalText = text;
        setTitle(file.getName()); // change the window title
        openedFile = file;
    }

    private void saveTextFile(File file) throws IOException {
//        if (!file.getName().endsWith(".txt")) {
//            file = new File(file.getAbsolutePath(), ".txt");
//        }
//        if (!file.exists()) file.createNewFile();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            char[] charArray = txtArea.getText().toCharArray();
            for (char c : charArray) {
                fos.write(c);
            }
        }
        openedFile = file;
        setTitle(openedFile.getName());
        //((Stage) root.getScene().getWindow()).setTitle(openedFile.getName());
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
        //set initial directory for file chooser
        fileChooser.setInitialDirectory(new File(System.getenv("HOME"), "Desktop"));
        File file = fileChooser.showSaveDialog(root.getScene().getWindow());
        if (file != null) {
            saveTextFile(file);
        }
    }

    public void txtAreaOnKeyReleased(KeyEvent keyEvent) {
        Stage window = ((Stage) root.getScene().getWindow());
        if (!window.getTitle().startsWith("*")) window.setTitle("*" + window.getTitle());
    }

    public void mnItemNewOnAction(ActionEvent actionEvent) {
        txtArea.clear();
        txtArea.requestFocus();
        setTitle("Untitled Document 1");
        openedFile = null;
        //originalText = null;
    }

    private void saveOnClosing() {

    }
}
