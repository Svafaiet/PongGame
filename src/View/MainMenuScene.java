package View;

import Controller.Packets.ClientPacket;
import Controller.Packets.ClientPacketType;
import Controller.Packets.ServerPacketType;
import Model.Profile;
import View.utils.BarScene;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import org.omg.PortableInterceptor.SUCCESSFUL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
                AppGUI.getGameStage().setScene(new SelectGameMenu());
                AppGUI.getGameStage().show();
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
        try {
            AppGUI.sendPacket(ServerPacketType.GET_RANKS);
            ClientPacket handShakePacket = AppGUI.getHandShakingPacket();
            AppGUI.setStageScene(new RanksScene(new ArrayList<>((ArrayList<Profile>) handShakePacket.getArgument(0))));
            // FIXME: 6/1/2018 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBar(){
        Button back = new Button("Log out");
        back.setPrefWidth(MAIN_MENU_WIDTH/10);
        getBar().getChildren().addAll(back);
        getBar().setAlignment(Pos.CENTER_LEFT);

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    AppGUI.sendPacket(ServerPacketType.LOG_OUT, AppGUI.getClientName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(AppGUI.getHandShakingPacket().getPacketType() == ClientPacketType.SUCCESSFUL_LOGOUT) {
                    AppGUI.getGameStage().setScene(new LoginScene());
                }
            }
        });
    }
}
