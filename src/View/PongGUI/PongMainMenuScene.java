package View.PongGUI;

import Controller.Packets.ClientPacket;
import Controller.Packets.ClientPacketType;
import Controller.Packets.ServerPacketType;
import Model.Game;
import Model.GameMode;
import Model.GameType;
import Model.WaitingGame;
import View.AppGUI;
import View.ConstantColors;
import View.SelectGameMenu;
import View.utils.BarScene;
import View.utils.ErrorText;
import View.utils.PreWrittenTextField;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

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
        firstElement.setPadding(new Insets(0, 15, 0, 15));

        HBox secondElement = new HBox(20);
        secondElement.setAlignment(Pos.CENTER);
        secondElement.setPadding(new Insets(10, 0, 20, 0));

        Button close = new Button("X");
        Region fillerRegion = new Region();
        HBox.setHgrow(fillerRegion, Priority.ALWAYS);
        close.setId("closeButton");
        Label label = new Label("Make newGame");
        label.setStyle("-fx-text-fill: #76c7d3");
        firstElement.getChildren().addAll(label, fillerRegion, close);

        TextField gameNameField = new PreWrittenTextField("Game Name");
        TextField secondPlayerField = new PreWrittenTextField("Second Player");
        Button start = new Button("Start!");
        start.setStyle("-fx-min-height: 20");
        start.setOnMouseClicked(event -> {
            try {
                AppGUI.getWorld().makeNewGame(AppGUI.getClientName(), gameNameField.getText(), GameMode.SINGLE_PLAYER, GameType.PONG);

                if (!AppGUI.getWorld().hasProfile(secondPlayerField.getText())) {
                    AppGUI.getWorld().addNewProfile(secondPlayerField.getText());
                }
                AppGUI.getWorld().addPlayerToGame(secondPlayerField.getText(), gameNameField.getText());
                AppGUI.setStageScene(new PongScene(AppGUI.getWorld().getRunningGame(gameNameField.getText())));
            } catch (Exception e) {
                e.printStackTrace();
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

        makeGameButton.setOnMouseClicked(event -> showSelectHowToLanGame());

        joinGameButton.setOnMouseClicked(event -> showSelectHowToConnect());
    }

    public void showSelectHowToLanGame() {
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

//        hostGameButton.setOnMouseClicked();
        makeGameOnServerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showHowToMakeAGameInServer();
            }
        });
    }

    public void showHowToMakeAGameInServer() {
        Rectangle container = new Rectangle();
        dropShadow(container);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPrefWidth(getMainMenuWidth()*2/3);

        HBox firstElement = new HBox(60);
        firstElement.setAlignment(Pos.CENTER);
        firstElement.setPadding(new Insets(0, 15, 0, 15));

        HBox secondElement = new HBox(20);
        secondElement.setAlignment(Pos.CENTER);
        secondElement.setPadding(new Insets(10, 0, 20, 0));

        Button close = new Button("X");
        Region fillerRegion = new Region();
        HBox.setHgrow(fillerRegion, Priority.ALWAYS);
        close.setId("closeButton");
        Label label = new Label("Make Game On Server");
        label.setStyle("-fx-text-fill: #76c7d3");
        firstElement.getChildren().addAll(label, fillerRegion, close);

        TextField gameNameField = new PreWrittenTextField("Game Name");
        Button make = new Button("Make!");
        make.setStyle("-fx-min-height: 20");
        make.setOnMouseClicked(event -> {
            try {
                AppGUI.sendPacket(ServerPacketType.MAKE_GAME, GameType.PONG, gameNameField.getText());
                ClientPacket clientPacket = AppGUI.getHandShakingPacket();
                if(clientPacket.getPacketType() == ClientPacketType.GAME_STARTED) {
                    AppGUI.setStageScene(new PongScene((Game) clientPacket.getArgument(1)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        secondElement.getChildren().addAll(gameNameField, make);

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

    private void dropShadow(Rectangle container) {
        container.setHeight(getMainMenuHeight()*9/10);
        container.setWidth(getMainMenuWidth());
        container.setLayoutY(getMainMenuHeight()/10 + 9);
        container.setFill(Color.color(0, 0, 0, 0.2));
    }

    public void showSelectHowToConnect() {
        try {
            AppGUI.sendPacket(ServerPacketType.GET_AVAILABLE_GAMES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ClientPacket clientPacket = AppGUI.getHandShakingPacket();
        if(clientPacket.getPacketType() != ClientPacketType.WAITING_GAMES) {
            return;
        }
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
        for(WaitingGame waitingGame : (ArrayList<WaitingGame>)clientPacket.getArgument(0)) {
            Button newGame = new Button(waitingGame.getSaveName() +
                    "(" + waitingGame.getProfiles().size() + "/" + waitingGame.getGameMaker().getPlayerCount()+ ")");
            vBox.getChildren().add(newGame);
            newGame.setOnMouseClicked(event -> {
                try {
                    AppGUI.sendPacket(ServerPacketType.JOIN, waitingGame.getSaveName());
                    ClientPacket clientPacket2 = AppGUI.getHandShakingPacket();
                    if(clientPacket2.getPacketType() == ClientPacketType.GAME_STARTED) {
                        AppGUI.setStageScene(new PongScene((Game) clientPacket2.getArgument(1)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            newGame.setStyle("-fx-min-height: 20;");
            newGame.setPrefWidth(getMainMenuWidth()/3);
        }
        if(vBox.getChildren().size() == 1) {
            vBox.getChildren().addAll(new Label("no game found"));
        }

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
