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
    private int caretPosition = 0;

    private boolean compiled;

    public void initialize() {
        btnFindNext.setDisable(true);
        txtFind.textProperty().addListener((observable, oldValue, newValue) -> {
            btnFindNext.setDisable(newValue.isBlank());
            compiled = false; // word changed
        });
    }

    public void btnCancelOnAction(ActionEvent event) {

    }

    public void btnFindNextOnAction(ActionEvent event) {
        if (!txtFind.getText().isBlank()) {
            caretPosition = mainViewController.txtArea.getCaretPosition(); // get the current caret position in notepad text area

            System.out.println("compiled = " + compiled);
            if (!compiled) { //if text or radio button selection changed
                find = txtFind.getText();

                if (!chkBoxMatchCase.isSelected()) {
                    System.out.println("don't check case");
                    find = find.toLowerCase(); // change case of find text to lowercase
                    text = text.toLowerCase(); // change case of text taken from text area to lowercase
                } else {
                    System.out.println("check case");
                }

                pattern = Pattern.compile(find);
                matcher = pattern.matcher(text);
                compiled = true; // compiled the pattern
            }

            if (matcher.find(caretPosition)) {
                System.out.println("finding next index of word");
                int startIndex = matcher.start();
                int endIndex = matcher.end();
                System.out.printf("Start Index: %d, End Index: %d", startIndex, endIndex);
                mainViewController.selectTheFoundText(startIndex, endIndex);
                caretPosition = endIndex;
            } else new Alert(Alert.AlertType.INFORMATION, "Cannot find text '" + find + "'").show();
        }
    }

    public void initData(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public void setTextToFind(String text) {
        this.text = text;
    }


}
