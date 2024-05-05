package lk.ijse.dep12.fx.note_pad.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public MenuItem mnItemExit;
    public MenuItem mnItemPrint;
    public Label lblColRow;
    public MenuItem mnItemUndo;
    public MenuItem mnItemFind;
    //private File file = null;
    private File openedFile = null;
    //String originalText = null;

    public Stage window;

    private int currentRow = 0;
    private int currentColumn = 0;

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
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        AnchorPane container = fxmlLoader.load();
        MainViewController controller = fxmlLoader.getController();
        controller.window = newWindowStage;

        Scene aboutScene = new Scene(container);
        newWindowStage.setScene(aboutScene);
        newWindowStage.setTitle("Untitled Document " + ++untitledDocumentCount);
        newWindowStage.centerOnScreen();
        newWindowStage.show();

        newWindowStage.setOnCloseRequest(event -> {
            if (controller.getWindowTitle().startsWith("*")) { // if there is unsaved data
                event.consume();
                controller.displayClosingAlert(); // display closing alert to the user
            }
        });
    }

    private String getTitle() {
        return ((Stage) root.getScene().getWindow()).getTitle();

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
            if ((openedFile != null && !openedFile.equals(file)) || (openedFile == null && getTitle().startsWith("*"))) { // if already opened a file here or started to edit in empty text area
                Stage newStage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
                AnchorPane container = fxmlLoader.load();
                newStage.setScene(new Scene(container));
                newStage.show();
                MainViewController controller = fxmlLoader.getController();

                // to assign on close alert
                controller.window = newStage;
                newStage.setOnCloseRequest(event -> {
                    if (controller.getWindowTitle().startsWith("*")) { // if there is unsaved data
                        event.consume();
                        controller.displayClosingAlert(); // display closing alert to the user
                    }
                });

                if (controller != null) controller.loadTextToTextArea(file);


            } else {
                loadTextToTextArea(file);
                openedFile = file;
                setTitle(openedFile.getName());
            }
        } else if (openedFile == null) {
            setTitle("Untitled Document");
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
        setTitle(file.getName()); // change the window title
        openedFile = file;
    }

    private void saveTextFile(File file) throws IOException {

        if (!file.getName().endsWith(".txt")) {
            File tempFile = new File(file.getAbsolutePath() + ".txt");
            if (file.exists()) file.delete();
            file = tempFile;
        }
        try (FileOutputStream fos = new FileOutputStream(file)) {
            char[] charArray = txtArea.getText().toCharArray();
            for (char c : charArray) {
                fos.write(c);
            }
        }
        openedFile = file;
        setTitle(openedFile.getName());
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
//            if (!file.getName().endsWith(".txt")){
//                File tempFile=file.renameTo()
//            }
            saveTextFile(file);
        }
    }

    public void txtAreaOnKeyReleased(KeyEvent keyEvent) {
        Stage window = ((Stage) root.getScene().getWindow());
        if (!window.getTitle().startsWith("*")) window.setTitle("*" + window.getTitle());

        if (keyEvent.getCode() == KeyCode.ENTER) {
            currentRow++; //line must be next line
            currentColumn = 1; // column must be 1
            lblColRow.setText("ln " + currentRow + ", Col " + currentColumn); // setting values to status label

        } else {
            getCurrentLineAndColumn();

        }
    }

    private void getCurrentLineAndColumn() {
        String[] lines = (txtArea.getText().substring(0, txtArea.getCaretPosition())).split("\n");
        currentRow = lines.length;
        if (txtArea.getCaretPosition() != 0 && (txtArea.getText().charAt(txtArea.getCaretPosition() - 1)) == '\n') { // if cursor is just after a new line
            currentRow++; //line must be next line
            currentColumn = 1; // column must be 1
        } else {
            currentColumn = lines[lines.length - 1].length(); // if not just after new line, column = length of line
        }

        lblColRow.setText("ln " + currentRow + ", Col " + currentColumn); // setting values to status label
    }

    public void mnItemNewOnAction(ActionEvent actionEvent) throws IOException {
        if (!getTitle().startsWith("*")) {
            txtArea.clear();
            txtArea.requestFocus();
            setTitle("Untitled Document 1");
            openedFile = null;
        } else {
            mnItmNewWindowOnAction(new ActionEvent());
        }

    }

    public void mnItemExitOnAction(ActionEvent actionEvent) {
        if (getWindowTitle().startsWith("*")) { // if there is unsaved data
            displayClosingAlert(); // display closing alert to the user
        } else {
            window.close();
        }

    }

    public void mnItemPrintOnAction(ActionEvent actionEvent) {
        PrinterJob job = PrinterJob.createPrinterJob();
        Node node = txtArea;
        if (job != null && job.showPrintDialog(window)) {
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
        }

    }

    public void txtAreaOnMouseClicked(MouseEvent mouseEvent) {
        getCurrentLineAndColumn();
    }

    public void mnItemUndoOnAction(ActionEvent actionEvent) {
        txtArea.undo();
    }

    public void mnItemCutOnAction(ActionEvent actionEvent) {
        txtArea.cut();
    }

    public void mnItemCopyOnAction(ActionEvent actionEvent) {
        txtArea.copy();
    }

    public void mnItemPasteOnAction(ActionEvent actionEvent) {
        txtArea.paste();
    }

    public void mnItemDeleteOnAction(ActionEvent actionEvent) {
        txtArea.deleteText(txtArea.getSelection());
    }

    public void mnItemSelectAllOnAction(ActionEvent actionEvent) {
        txtArea.selectAll();
    }

    public void mnItemFindOnAction(ActionEvent actionEvent) {
        Stage findStage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/FindView.fxml"));
            AnchorPane root = fxmlLoader.load();
            Scene scene = new Scene(root);
            findStage.setScene(scene);
            FindViewController controller = fxmlLoader.getController();
            controller.initData(this);
            controller.setTextToFind(txtArea.getText());
            findStage.setTitle("Find");
            findStage.show();
            findStage.centerOnScreen();
            findStage.setResizable(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void selectTheFoundText(int startIndex, int endIndex) {
        txtArea.selectRange(startIndex, endIndex);
    }

    public String getTextFromTextArea() {
        return txtArea.getText();
    }
}
