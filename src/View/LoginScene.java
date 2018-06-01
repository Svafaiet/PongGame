package View;

import Controller.Client;
import Controller.Packets.ClientPacketType;
import Controller.Packets.ServerPacketType;
import Model.Exceptions.DuplicatePlayerNameException;
import Model.GameMode;
import Model.GameType;
import View.PongGUI.PongScene;
import View.utils.BarScene;
import View.utils.ErrorText;
import View.utils.PreWrittenTextField;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LoginScene extends BarScene {

    public LoginScene(){
        super(new Group(), MainMenuScene.MAIN_MENU_WIDTH, MainMenuScene.MAIN_MENU_HEIGHT, ConstantColors.BACK_GROUND_COLOR1);
        Group root = getMainPart();
        root.getStylesheets().addAll("View/Styles.css");

        VBox vBox = new VBox(12);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefSize(MainMenuScene.MAIN_MENU_WIDTH, MainMenuScene.MAIN_MENU_HEIGHT);

        Label loginText = new Label("Enter your Name to Login");

        HBox loginBox = new HBox(5);
        TextField loginField = new TextField();
        Button loginButton = new Button("Login");
        loginBox.getChildren().addAll(loginField, loginButton);
        loginBox.setAlignment(Pos.CENTER);
        loginButton.setStyle("-fx-min-height: 20");

        ErrorText errorText = new ErrorText();

        vBox.getChildren().addAll(loginText, loginBox, errorText);
        root.getChildren().addAll(vBox);

        EventHandler<InputEvent> eventHandler = event -> {
            String profileName = loginField.getText();
            if(profileName.equals("")) {
                errorText.showError("Please Enter Your Name");
            } else {
                try {
                    AppGUI.sendPacket(ServerPacketType.LOG_IN, loginField.getText());
                    if(AppGUI.getHandShakingPacket().getPacketType() == ClientPacketType.SUCCESSFUL_LOGIN) {
                        AppGUI.setStageScene(new MainMenuScene());
                    } else {
                        // FIXME: 6/1/2018
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        loginButton.setOnMouseClicked(eventHandler);
        loginBox.setOnKeyPressed(eventHandler);

        setBar();
    }

    private void setBar() {
        Button signUp = new Button("Sign Up");
        signUp.setStyle("-fx-min-height: 30");
        signUp.setPrefWidth(MainMenuScene.MAIN_MENU_WIDTH/8);
        getBar().getChildren().addAll(signUp);
        getBar().setAlignment(Pos.CENTER_LEFT);
        signUp.setOnMouseClicked(event -> showHowToSignUP());
    }

    private void dropShadow(Rectangle container) {
        container.setHeight(MainMenuScene.MAIN_MENU_HEIGHT);
        container.setWidth(MainMenuScene.MAIN_MENU_WIDTH);
        container.setFill(Color.color(0, 0, 0, 0.2));
    }

    private void showHowToSignUP() {
        Rectangle container = new Rectangle();
        dropShadow(container);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPrefWidth(MainMenuScene.MAIN_MENU_WIDTH*2/5);

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
        Label label = new Label("Register");
        label.setStyle("-fx-text-fill: #76c7d3");
        firstElement.getChildren().addAll(label, fillerRegion, close);

        TextField accountName = new PreWrittenTextField("Account Name");
        Button signUp = new Button("Sign Up");
        signUp.setStyle("-fx-min-height: 20");
        signUp.setOnMouseClicked(event -> {
            try {
                AppGUI.sendPacket(ServerPacketType.SIGN_UP, accountName.getText());
                if(AppGUI.getHandShakingPacket().getPacketType() == ClientPacketType.SUCCESSFUL_LOGIN) {
                    AppGUI.setStageScene(new MainMenuScene());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        secondElement.getChildren().addAll(accountName, signUp);

        vBox.getChildren().addAll(firstElement, secondElement);

        vBox.setId("floatingMenu");
        AppGUI.getGameStage().show();
        ((Group) AppGUI.getGameStage().getScene().getRoot()).getChildren().addAll(container, vBox);
        vBox.relocate((MainMenuScene.MAIN_MENU_WIDTH - vBox.getPrefWidth())/2,
                (MainMenuScene.MAIN_MENU_HEIGHT - vBox.getPrefHeight() - MainMenuScene.MAIN_MENU_HEIGHT/5)/2);

        close.setOnMouseClicked(event -> {
            ((Group) AppGUI.getGameStage().getScene().getRoot()).getChildren().remove(vBox);
            ((Group) AppGUI.getGameStage().getScene().getRoot()).getChildren().remove(container);
        });

        container.setOnMouseClicked(close.getOnMouseClicked());
    }

}
