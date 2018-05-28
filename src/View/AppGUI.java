package View;

import Controller.ClientController;
import Model.Player;
import Model.Pong.PongLogic;
import Model.Pong.PongMaker;
import Model.Pong.PongPlayer;
import Model.Profile;
import Model.World;
import View.PongGUI.PongScene;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sun.plugin.services.WPlatformService;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public final class AppGUI extends Application {

    private static Stage gameStage;
    private static Socket client;

    public static Stage getGameStage() {
        return gameStage;
    }

    public static World getGameServerWorld() {
        return ClientController.Instance.getMainServerWorld();
    }

    public static World getWorld() {
        return ClientController.Instance.getWorld();
    }

    public static Socket getClient() {
        return client;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        gameStage = new Stage();
        getGameStage().setTitle("Game Center");
        getGameStage().setResizable(false);
        ArrayList<Player> players = new ArrayList<>();
        PongPlayer pongPlayer = new PongPlayer();
        pongPlayer.setPlayerProfile(new Profile("ali"));
        PongPlayer pongPlayer2 = new PongPlayer();
        pongPlayer2.setPlayerProfile(new Profile("mali"));
        players.add(pongPlayer);
        players.add(pongPlayer2);
        setStageScene(new PongScene((PongLogic)(new PongMaker()).makeNewGame(players)));
        getGameStage().show();

    }



    public static void setStageScene(Scene scene) {
        AppGUI.getGameStage().setScene(scene);
        getGameStage().sizeToScene();

    }

    public static void makeVBoxType1(VBox vBox) {
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPadding(new Insets(vBox.getPrefHeight()/4, vBox.getPrefWidth()/7,
                vBox.getPrefHeight()/4, vBox.getPrefWidth()/7));
        vBox.setStyle("-fx-border-color: #76c7d3");
    }

}
