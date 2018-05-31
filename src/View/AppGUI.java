package View;

import Controller.Client;
import Controller.Packets.ServerPacket;
import Controller.Packets.ServerPacketType;
import Model.World;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.Socket;

public final class AppGUI extends Application {

    private static Stage gameStage;

    public static Stage getGameStage() {
        return gameStage;
    }



    public static World getWorld() {
        return Client.getInstance().getWorld();
    }

    public static String getClientName() {
        return Client.getInstance().getClientName();
    }

    public static void sendPacket(ServerPacketType serverPacketType, String... packetElements) {
        ServerPacket serverPacket = new ServerPacket(Client.getInstance().getClientName());
        serverPacket.setPacketType(serverPacketType);
        serverPacket.addElements(packetElements);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        gameStage = new Stage();
        getGameStage().setTitle("Game Center");
        getGameStage().setResizable(false);
        setStageScene(new LoginScene());
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
