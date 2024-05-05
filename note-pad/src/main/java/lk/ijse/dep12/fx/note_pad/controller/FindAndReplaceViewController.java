package lk.ijse.dep12.fx.note_pad.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindAndReplaceViewController {

    public Button btnCancel;

    public Button btnFindNext;

    public Button btnReplace;

    public Button btnReplaceAll;

    public CheckBox chkBoxMatchCase;

    public CheckBox chkBoxWrapAround;

    public TextField txtFind;

    public TextField txtReplace;

    private boolean compiled;
    private MainViewController mainViewController;
    private String text;
    private String find = "";
    private Matcher matcher = null;


    public void initialize() {
        //disable find and replace buttons when the form is opening
        btnFindNext.setDisable(true);
        btnReplaceAll.setDisable(true);
        btnReplace.setDisable(true);

        txtFind.textProperty().addListener((observable, oldValue, newValue) -> {
            btnFindNext.setDisable(newValue.isBlank());
            if (!newValue.equals(oldValue))
                compiled = false; // if the word has changed, have to compile the pattern again
        });

        txtReplace.textProperty().addListener((observable, oldValue, newValue) -> {
            btnReplace.setDisable(newValue.isBlank());
            btnReplaceAll.setDisable(newValue.isBlank());
        });


    }

    public void btnCancelOnAction(ActionEvent event) {

    }

    public void btnFindNextOnAction(ActionEvent event) {
        if (!txtFind.getText().isBlank()) {
            int caretPosition = mainViewController.txtArea.getCaretPosition(); // get the current caret position in notepad text area

            System.out.println("compiled = " + compiled);
            if (!compiled) { //if text or check box selection changed
                find = txtFind.getText();
                text = mainViewController.getTextFromTextArea();

                if (!chkBoxMatchCase.isSelected()) {
                    System.out.println("don't check case");
                    find = find.toLowerCase(); // change case of find text to lowercase
                    text = text.toLowerCase(); // change case of text taken from text area to lowercase
                }
                //System.out.println("find is " + find + " text is " + text);

                Pattern pattern = Pattern.compile(find);
                matcher = pattern.matcher(text);
                compiled = true; // compiled the pattern
            }

            if (matcher.find(caretPosition)) {
                //System.out.println("finding next index of word");
                int startIndex = matcher.start();
                int endIndex = matcher.end();
                //System.out.printf("Start Index: %d, End Index: %d", startIndex, endIndex);
                mainViewController.selectTheFoundText(startIndex, endIndex); // highlight matching text
                mainViewController.txtArea.selectPositionCaret(endIndex); // change caret position in text area
            } else if (!chkBoxWrapAround.isSelected()) { // if search is not wrapped around
                new Alert(Alert.AlertType.INFORMATION, "Cannot find text '" + find + "'").show();
            } else if (chkBoxWrapAround.isSelected()) { // if search is wrapped around
                mainViewController.txtArea.positionCaret(0);
                //mainViewController.txtArea.deselect();
            }
        }
    }

    public void btnReplaceAllOnAction(ActionEvent event) {
        if (!(txtReplace.getText().isEmpty() || txtFind.getText().isEmpty())) {
            String newText = "";
            if (chkBoxMatchCase.isSelected()) { // case matched
                newText = mainViewController.txtArea.getText().replaceAll(txtFind.getText(), txtReplace.getText());
            } else {
                newText = mainViewController.txtArea.getText();
                newText = newText.replaceAll("(?i)" + txtFind.getText(), txtReplace.getText());
            }

            mainViewController.txtArea.setText(newText);
        }
    }

    public void btnReplaceOnAction(ActionEvent event) {
        //mainViewController.txtArea.getText().replace(txtReplace.getText());
    }

    public void chkBoxMatchCaseOnAction(ActionEvent event) {
        compiled = false;
    }

    public void initData(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
        //if cursor was at the end of end when opening find window, move the cursor to beginning of text
        if (mainViewController.txtArea.getCaretPosition() == mainViewController.getTextFromTextArea().length()) {
            mainViewController.txtArea.positionCaret(0);
        }
    }

    public void setTextToFind(String text) {
        this.text = text;
    }
}
