package View.utils;


import javafx.scene.control.TextField;

public class PreWrittenTextField extends TextField {
    private boolean hasClicked = false;

    public PreWrittenTextField(String text) {
        super(text);
        if(hasClicked) {
            setOnMouseClicked(event -> {});
        } else {
            setOnMouseClicked(event -> {this.setText("");});
            hasClicked = true;
        }

    }
}
