package View.PongGUI;

import View.MainMenuScene;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.awt.*;

public class BarScene extends Scene {
    private HBox bar = new HBox();
    private Group mainPart = new Group();

    public BarScene(Parent root, double width, double length, Paint paint) {
        super(root, width, length, paint);
        VBox container = new VBox();
        container.getChildren().addAll(bar, mainPart);
        ((Group) root).getChildren().addAll(container);
        root.getStylesheets().addAll("View/Styles.css");
    }

    public BarScene(Parent root, double width, double length) {
        super(root, width, length);
        VBox container = new VBox();
        container.getChildren().addAll(bar, mainPart);
        ((Group) root).getChildren().addAll(container);
        root.getStylesheets().addAll("View/Styles.css");
    }

    public HBox getBar(){
        return bar;
    }

    public Group getMainPart() {
        return mainPart;
    }
}
