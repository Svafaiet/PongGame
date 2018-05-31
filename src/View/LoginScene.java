package View;

import Controller.Client;
import Model.Exceptions.DuplicatePlayerNameException;
import View.utils.ErrorText;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginScene extends Scene {

    public LoginScene(){
        super(new Group(), MainMenuScene.MAIN_MENU_WIDTH, MainMenuScene.MAIN_MENU_HEIGHT, ConstantColors.BACK_GROUND_COLOR1);
        Group root = (Group) getRoot();
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
                // FIXME: 5/28/2018
                if(!AppGUI.getWorld().hasProfile(profileName)) {
                    try {
                        AppGUI.getWorld().addNewProfile(profileName);
                    } catch (DuplicatePlayerNameException e) {
                        e.printStackTrace();
                    }
                    Client.getInstance().setClientName(profileName);
                }
                AppGUI.setStageScene(new MainMenuScene());
            }
        };

        loginButton.setOnMouseClicked(eventHandler);
        loginBox.setOnKeyPressed(eventHandler);
    }

}
