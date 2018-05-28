package View;

import Model.Profile;
import View.utils.BarScene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;

public class RanksScene extends BarScene {
    public static final double RANKS_MENU_WIDTH = AppGUI.getGameStage().getWidth();
    public static final double RANKS_MENU_HEIGHT = AppGUI.getGameStage().getHeight();

    public RanksScene(ArrayList<Profile> profiles){
        super(new Group(), RANKS_MENU_WIDTH, RANKS_MENU_HEIGHT, ConstantColors.BACK_GROUND_COLOR1);
        Group root = getMainPart();

        VBox vBox = new VBox(3);
        vBox.setStyle("-fx-border-color: #6ebac5;");
        Collections.sort(profiles);
        int lastWiningCount = -1;
        int lastIndex = 1;
        int index = 0;
        for(Profile profile : profiles) {
            index++;
            if(profile.getWinCount() != lastWiningCount) {
                lastIndex = index;
                lastWiningCount = profile.getWinCount();
            }
            HBox hBox = new HBox(50);
            hBox.setPrefWidth(RANKS_MENU_WIDTH);
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(0, 0, 0, 10));
            hBox.getChildren().addAll(new Label(lastIndex + "." + profile.getName()), new Label(String.valueOf(profile.getWinCount())));
            hBox.setId("rankRectangle");
            vBox.getChildren().addAll(hBox);

        }
        root.getChildren().addAll(vBox);
        setBar();
    }

    private void setBar() {
        Button back = new Button("back");
        back.setPrefWidth(RANKS_MENU_WIDTH/10);
        getBar().getChildren().addAll(back);
        getBar().setAlignment(Pos.CENTER_LEFT);

        back.setOnMouseClicked(event -> AppGUI.setStageScene(new MainMenuScene()));
    }
}
