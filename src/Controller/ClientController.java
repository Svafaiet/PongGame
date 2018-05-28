package Controller;

import Model.World;
import View.AppGUI;
import javafx.application.Application;

public class ClientController {
    public static final ClientController Instance = new ClientController();
    private String clientName;
    private World mainServerWorld;
    private World world;

    private ClientController(){
        world = new World();
    }

    public static void main(String[] args) {
        Application.launch(AppGUI.class, args);

    }

    public World getWorld() {
        return world;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public World getMainServerWorld() {
        return mainServerWorld;
    }
}
