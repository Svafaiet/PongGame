package View.utils;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ErrorText extends Text {
    public ErrorText(String text){
        super(text);
        setFill(Color.RED);
    }
}
