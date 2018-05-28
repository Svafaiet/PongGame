package View;

import Model.GameType;
import View.PongGUI.BarScene;
import View.PongGUI.PongMainMenuScene;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;


public class SelectGameMenu extends BarScene {
    public static final double SELECT_MENU_WIDTH = AppGUI.gameStage.getWidth();
    public static final double SELECT_MENU_HEIGHT = AppGUI.gameStage.getHeight();



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
                AppGUI.gameStage.setScene(new MainMenuScene());
                AppGUI.gameStage.show();
            }
        });
    }

    private void chooseGame(GameType gameType) {
        switch (gameType) {
            case PONG:
                AppGUI.gameStage.setScene(new PongMainMenuScene());
                AppGUI.gameStage.show();
                break;
        }
    }
}
