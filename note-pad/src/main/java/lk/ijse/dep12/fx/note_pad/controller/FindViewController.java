package lk.ijse.dep12.fx.note_pad.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindViewController {

    public Button btnCancel;

    public Button btnFindNext;

    public CheckBox chkBoxMatchCase;

    public CheckBox chkBoxWrapAround;

    public RadioButton rdBtnDown;

    public ToggleGroup rdBtnGroup;

    public RadioButton rdBtnUp;

    public TextField txtFind;

    private MainViewController mainViewController;
    private String text;

    private String find = "";
    private Pattern pattern = null;
    private Matcher matcher = null;
    private int caretPosition = -1;

    private boolean compiled;

    public void initialize() {
        btnFindNext.setDisable(true);
        txtFind.textProperty().addListener((observable, oldValue, newValue) -> {
            btnFindNext.setDisable(newValue.isBlank());
            if (!newValue.equals(oldValue))
                compiled = false; // if the word has changed, have to compile the pattern again
        });
    }

    public void btnCancelOnAction(ActionEvent event) {

    }

    public void btnFindNextOnAction(ActionEvent event) {
        if (!txtFind.getText().isBlank()) {
            caretPosition = mainViewController.txtArea.getCaretPosition(); // get the current caret position in notepad text area

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

                pattern = Pattern.compile(find);
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


    public void chkBoxMatchCaseOnAction(ActionEvent actionEvent) {
        compiled = false;
    }
}
