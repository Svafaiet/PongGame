package View;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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

        Text errorText = new Text("");
        errorText.setStyle("-fx-text-fill: red;");

        vBox.getChildren().addAll(loginText, loginBox, errorText);
        root.getChildren().addAll(vBox);
    }

}
