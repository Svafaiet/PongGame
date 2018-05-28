package View.utils;

import javafx.animation.FadeTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ErrorText extends Text {
    public ErrorText(){
        super();
        setFill(Color.RED);
    }

    public void showError(String errorText){
        setText(errorText);
        // FIXME: 5/28/2018
        new Thread(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setText("");
        }).start();
    }
}
