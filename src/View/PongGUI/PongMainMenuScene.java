package View.PongGUI;

import Model.WaitingGame;
import View.AppGUI;
import View.ConstantColors;
import View.SelectGameMenu;
import View.utils.BarScene;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PongMainMenuScene extends BarScene {
    public static double getMainMenuWidth(){
//        AppGUI.gameStage.show();
        return AppGUI.gameStage.getWidth();
    }
    public static double getMainMenuHeight() {
//        AppGUI.gameStage.show();
        return AppGUI.gameStage.getHeight();
    }
    private VBox options = new VBox(2);

    public PongMainMenuScene() {
        super(new Group(), getMainMenuWidth(), getMainMenuHeight(), ConstantColors.BACK_GROUND_COLOR1);
        Group root = this.getMainPart();
        HBox subMenus = new HBox();
        VBox gameModes = new VBox(2);

        subMenus.getChildren().addAll(gameModes, options);
        Button customGameButton = new Button("Custom Game");
        Button lanModeButton = new Button("LAN");
        customGameButton.setPrefWidth(getMainMenuWidth()*2/5);
        lanModeButton.setPrefWidth(getMainMenuWidth()*2/5);

        gameModes.getChildren().addAll(customGameButton, lanModeButton);

        root.getChildren().addAll(subMenus);

        //gameModes configuration:
        gameModes.setPrefSize(getMainMenuWidth()*2/5, getMainMenuHeight()*9/10);
        AppGUI.makeVBoxType1(gameModes);

        //options configuration:
        options.setPrefSize(getMainMenuWidth()*3/5, getMainMenuHeight()*9/10);
        AppGUI.makeVBoxType1(options);

        //computerModeButton
        customGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                makeOptionsCustomGame();
            }
        });

        //lanModeButton
        lanModeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                makeOptionsLANMode();
            }
        });

        setBar();
    }

    public void makeOptionsCustomGame(){
        Button newGameButton = new Button("New Game");
        Button loadGameButton = new Button("Load Game");
        newGameButton.setPrefWidth(0.70*getMainMenuWidth()*3/5);
        loadGameButton.setPrefWidth(0.70*getMainMenuWidth()*3/5);
        options.getChildren().clear();
        options.getChildren().addAll(newGameButton, loadGameButton);
    }

    public void makeOptionsLANMode(){
        Button makeGameButton = new Button("Make Game");
        Button joinGameButton = new Button("Join Game");
        makeGameButton.setPrefWidth(0.70*getMainMenuWidth()*3/5);
        joinGameButton.setPrefWidth(0.70*getMainMenuWidth()*3/5);

        options.getChildren().clear();
        options.getChildren().addAll(makeGameButton, joinGameButton);

        makeGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showSelectHowToMakeGame();
            }
        });

        joinGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showSelectHowToConnect();
            }
        });
    }

    public void showSelectHowToMakeGame() {
        makeOptionsLANMode();
        Button hostGameButton = new Button("Host Game");
        Button makeGameOnServerButton = new Button("Make On Server");

        //styling button
        hostGameButton.setPrefWidth(0.30*getMainMenuWidth()*3/5);
        makeGameOnServerButton.setPrefWidth(0.30*getMainMenuWidth()*3/5);

        HBox howToMakeGameOptions = new HBox(15);
        howToMakeGameOptions.setAlignment(Pos.CENTER);
        howToMakeGameOptions.getChildren().addAll(hostGameButton, makeGameOnServerButton);

        options.getChildren().add(1, howToMakeGameOptions);

    }

    private void dropShadow(Rectangle container) {
        container.setHeight(getMainMenuHeight()*9/10);
        container.setWidth(getMainMenuWidth());
        container.setLayoutY(getMainMenuHeight()/10 + 9);
        container.setFill(Color.color(0, 0, 0, 0.2));
    }

    public void showSelectHowToConnect() {
        boolean reset = false;
        Rectangle container = new Rectangle();
        dropShadow(container);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPrefWidth(getMainMenuWidth()/3);

        HBox firstElement = new HBox(60);
        firstElement.setAlignment(Pos.CENTER);

        Button close = new Button("X");
        close.setId("closeButton");
        Label label = new Label("Select Game");
        label.setStyle("-fx-text-fill: #76c7d3");
        firstElement.getChildren().addAll(label, close);

        vBox.getChildren().add(firstElement);
        for(WaitingGame waitingGame : AppGUI.world.getWaitingGames()) {
            Button newGame = new Button(waitingGame.getSaveName() +
                    "(" + waitingGame.getProfiles().size() + "/" + waitingGame.getGameMaker().getPlayerCout()+ ")");
            newGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    // TODO: 5/24/2018
                }
            });
            vBox.getChildren().add(newGame);
        }
        if(vBox.getChildren().size() == 1) {
            vBox.getChildren().addAll(new Label("no game found"));
        }

        vBox.setId("floatingMenu");
        AppGUI.gameStage.show();
        ((Group)AppGUI.gameStage.getScene().getRoot()).getChildren().addAll(container, vBox);
        vBox.relocate((getMainMenuWidth() - vBox.getPrefWidth())/2,
                (getMainMenuHeight() - vBox.getPrefHeight() - getMainMenuHeight()/5)/2);

        close.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ((Group) AppGUI.gameStage.getScene().getRoot()).getChildren().remove(vBox);
                ((Group) AppGUI.gameStage.getScene().getRoot()).getChildren().remove(container);
            }
        });

        container.setOnMouseClicked(close.getOnMouseClicked());
    }

    public void setBar(){
        getBar().setAlignment(Pos.CENTER_LEFT);
        Button back = new Button("back");
        getBar().getChildren().addAll(back);
        back.setPrefHeight(getMainMenuHeight()/10);

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                AppGUI.gameStage.setScene(new SelectGameMenu());
                AppGUI.gameStage.show();
            }
        });
    }
}
