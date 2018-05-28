package View;

import Model.Pong.PongLogic;
import Model.World;
import View.PongGUI.PongMainMenuScene;
import View.PongGUI.PongScene;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.Socket;

public final class AppGUI extends Application {

    public static Stage gameStage;
    public static World world;
    public static Socket client;

    @Override
    public void start(Stage primaryStage) throws Exception {
        gameStage = new Stage();
        gameStage.setTitle("Game Center");
        gameStage.setResizable(false);
        gameStage.setScene(new PongScene(new PongLogic()));
        gameStage.show();
    }

    public static void makeVBoxType1(VBox vBox) {
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPadding(new Insets(vBox.getPrefHeight()/4, vBox.getPrefWidth()/7,
                vBox.getPrefHeight()/4, vBox.getPrefWidth()/7));
        vBox.setStyle("-fx-border-color: #76c7d3");
    }
}
