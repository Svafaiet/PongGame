package View.PongGUI;

import Model.Exceptions.DuplicateGameException;
import Model.Exceptions.DuplicatePlayerNameException;
import Model.Exceptions.PlayerNotFoundException;
import Model.Game;
import Model.GameType;
import Model.Pong.PongLogic;
import Model.WaitingGame;
import View.AppGUI;
import View.ConstantColors;
import View.SelectGameMenu;
import View.utils.BarScene;
import View.utils.ErrorText;
import View.utils.PreWrittenTextField;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PongMainMenuScene extends BarScene {
    public static double getMainMenuWidth(){
//        AppGUI.gameStage.show();
        return AppGUI.getGameStage().getWidth();
    }
    public static double getMainMenuHeight() {
//        AppGUI.gameStage.show();
        return AppGUI.getGameStage().getHeight();
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
        ErrorText errorText = new ErrorText();
        newGameButton.setPrefWidth(0.70*getMainMenuWidth()*3/5);
        loadGameButton.setPrefWidth(0.70*getMainMenuWidth()*3/5);
        options.getChildren().clear();
        options.getChildren().addAll(newGameButton, loadGameButton, errorText);


        loadGameButton.setOnMouseClicked(event -> {
            errorText.showError("Not Available at this Version");
        });

        newGameButton.setOnMouseClicked(event -> {
            showHowToMakeNewGame();
        });
    }

    private void showHowToMakeNewGame() {
        Rectangle container = new Rectangle();
        dropShadow(container);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPrefWidth(getMainMenuWidth()*2/3);

        HBox firstElement = new HBox(60);
        firstElement.setAlignment(Pos.CENTER);

        HBox secondElement = new HBox(20);
        secondElement.setAlignment(Pos.CENTER_LEFT);

        Button close = new Button("X");
        close.setId("closeButton");
        Label label = new Label("Make newGame");
        label.setStyle("-fx-text-fill: #76c7d3");
        firstElement.getChildren().addAll(label, close);

        TextField gameNameField = new PreWrittenTextField("Game Name");
        TextField secondPlayerField = new PreWrittenTextField("Second Player");
        Button start = new Button("Start!");
        start.setOnMouseClicked(event -> {
            try {
                AppGUI.getWorld().makeNewGame(AppGUI.getClientName(), gameNameField.getText(), GameType.PONG);

                if (!AppGUI.getWorld().hasProfile(secondPlayerField.getText())) {
                    AppGUI.getWorld().addNewProfile(secondPlayerField.getText());
                }
                AppGUI.getWorld().addPlayerToGame(secondPlayerField.getText(), gameNameField.getText());
                AppGUI.setStageScene(new PongScene(AppGUI.getWorld().getRunningGame(gameNameField.getText())));
            } catch (Exception e) {
            }
        });
        secondElement.getChildren().addAll(gameNameField, secondPlayerField, start);

        vBox.getChildren().addAll(firstElement, secondElement);

        vBox.setId("floatingMenu");
        AppGUI.getGameStage().show();
        ((Group) AppGUI.getGameStage().getScene().getRoot()).getChildren().addAll(container, vBox);
        vBox.relocate((getMainMenuWidth() - vBox.getPrefWidth())/2,
                (getMainMenuHeight() - vBox.getPrefHeight() - getMainMenuHeight()/5)/2);

        close.setOnMouseClicked(event -> {
            ((Group) AppGUI.getGameStage().getScene().getRoot()).getChildren().remove(vBox);
            ((Group) AppGUI.getGameStage().getScene().getRoot()).getChildren().remove(container);
        });

        container.setOnMouseClicked(close.getOnMouseClicked());
    }

    public void makeOptionsLANMode(){
        Button makeGameButton = new Button("Make Game");
        Button joinGameButton = new Button("Join Game");
        makeGameButton.setPrefWidth(0.70*getMainMenuWidth()*3/5);
        joinGameButton.setPrefWidth(0.70*getMainMenuWidth()*3/5);

        options.getChildren().clear();
        options.getChildren().addAll(makeGameButton, joinGameButton);

        makeGameButton.setOnMouseClicked(event -> showSelectHowToMakeGame());

        joinGameButton.setOnMouseClicked(event -> showSelectHowToConnect());
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
        for(WaitingGame waitingGame : AppGUI.getWorld().getWaitingGames()) {
            Button newGame = new Button(waitingGame.getSaveName() +
                    "(" + waitingGame.getProfiles().size() + "/" + waitingGame.getGameMaker().getPlayerCout()+ ")");
            newGame.setOnMouseClicked(event -> {
                // TODO: 5/24/2018
            });
            vBox.getChildren().add(newGame);
        }
        if(vBox.getChildren().size() == 1) {
            vBox.getChildren().addAll(new Label("no game found"));
        }


    }

    public void setBar(){
        getBar().setAlignment(Pos.CENTER_LEFT);
        Button back = new Button("back");
        getBar().getChildren().addAll(back);
        back.setPrefHeight(getMainMenuHeight()/10);

        back.setOnMouseClicked(event -> {
           AppGUI.setStageScene(new SelectGameMenu());
            AppGUI.getGameStage().show();
        });
    }
}
