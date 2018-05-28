package View;

import Model.Pong.PongLogic;
import Model.World;
import View.PongGUI.PongScene;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.Socket;

public final class AppGUI extends Application {

    private static Stage gameStage;
    private static World world;
    private static Socket client;

    public static Stage getGameStage() {
        return gameStage;
    }

    public static World getWorld() {
        return world;
    }

    public static Socket getClient() {
        return client;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        gameStage = new Stage();
        getGameStage().setTitle("Game Center");
        getGameStage().setResizable(false);
        setStageScene(new PongScene(new PongLogic()));
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
