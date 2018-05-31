package Controller;

import Model.World;
import View.AppGUI;
import javafx.application.Application;

public class Client {
    private static final Client Instance = new Client();
    private String clientName;
    private World world;

    private Client(){
        world = new World();
    }

    public static Client getInstance() {
        return Instance;
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
}
