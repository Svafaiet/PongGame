package View;

import Model.Profile;
import View.PongGUI.BarScene;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

import java.awt.*;
import java.util.ArrayList;

public class MainMenuScene extends BarScene {
    public static final double MAIN_MENU_WIDTH = Screen.getPrimary().getBounds().getWidth() / 2;
    public static final double MAIN_MENU_HEIGHT = Screen.getPrimary().getBounds().getHeight() / 2;


    public MainMenuScene() {
        super(new Group(), MAIN_MENU_WIDTH, MAIN_MENU_HEIGHT, ConstantColors.BACK_GROUND_COLOR1);
        Group root = getMainPart();

        VBox container = new VBox(6, new Group());
        Button ranksButton = new Button("Ranks");
        Button playButton = new Button("Play");
        container.getChildren().addAll(playButton, ranksButton);

        //resizing
        container.setPrefSize(MAIN_MENU_WIDTH, MAIN_MENU_HEIGHT);
        playButton.setPrefWidth(MAIN_MENU_WIDTH * 2 / 5);
        ranksButton.setPrefWidth(MAIN_MENU_WIDTH * 2 / 5);
        container.setAlignment(Pos.CENTER);

        root.getChildren().addAll(container);
        root.getStylesheets().addAll("View/Styles.css");

        playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                AppGUI.gameStage.setScene(new SelectGameMenu());
                AppGUI.gameStage.show();
            }
        });

        ranksButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showRanks();
            }
        });

        setBar();
    }

    public void showRanks() {
        // FIXME: 5/25/2018
        ArrayList<Profile> profiles = new ArrayList<>();
        profiles.add(new Profile("Ali"));
        profiles.add(new Profile("Naghi"));
        profiles.add(new Profile("Taghi"));
        profiles.get(0).setWinCount(1);
        profiles.get(1).setWinCount(1);
        AppGUI.gameStage.setScene(new RanksScene(profiles));
    }

    public void setBar(){
        Button back = new Button("back");
        back.setPrefWidth(MAIN_MENU_WIDTH/10);
        getBar().getChildren().addAll(back);
        getBar().setAlignment(Pos.CENTER_LEFT);

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                AppGUI.gameStage.setScene(new LoginScene());
            }
        });
    }
}
