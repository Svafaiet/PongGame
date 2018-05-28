package Controller;

import Model.World;
import View.AppGUI;
import javafx.application.Application;

public class ClientController {


    public static void main(String[] args) {
        World world = new World();
        Application.launch(AppGUI.class, args);

    }
}
