package View;

import Model.GameType;
import View.utils.BarScene;
import View.PongGUI.PongMainMenuScene;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;


public class SelectGameMenu extends BarScene {
    public static final double SELECT_MENU_WIDTH = AppGUI.getGameStage().getWidth();
    public static final double SELECT_MENU_HEIGHT = AppGUI.getGameStage().getHeight();



    public SelectGameMenu() {
        super(new Group(), SELECT_MENU_WIDTH, SELECT_MENU_HEIGHT, ConstantColors.BACK_GROUND_COLOR1);
        HBox hBox = new HBox(35);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPrefSize(SELECT_MENU_WIDTH, SELECT_MENU_HEIGHT*9/10);
        hBox.setStyle("-fx-border-color: #76c7d3;");

        for(GameType gameType : GameType.values()) {
            Button newButton = new Button(gameType.toString());
            newButton.setStyle("-fx-min-height: " + SELECT_MENU_HEIGHT/5 + ";" +
                    "-fx-min-width: " + SELECT_MENU_WIDTH/5 + ";");
            hBox.getChildren().addAll(newButton);
            newButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    chooseGame(gameType
                    );
                }
            });
        }
        getMainPart().getChildren().addAll(hBox);
        setBar();
    }

    private void setBar() {
        Button button = new Button("back");
        getBar().getChildren().addAll(button);
        getBar().setAlignment(Pos.CENTER_LEFT);
        getBar().setPrefHeight(SELECT_MENU_HEIGHT/10);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                AppGUI.setStageScene(new MainMenuScene());
                AppGUI.getGameStage().show();
            }
        });
    }

    private void chooseGame(GameType gameType) {
        switch (gameType) {
            case PONG:
                AppGUI.setStageScene(new PongMainMenuScene());
                AppGUI.getGameStage().show();
                break;
        }
    }
}
