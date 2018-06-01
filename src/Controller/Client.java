package Controller;

import Controller.Packets.ClientPacket;
import Controller.Packets.ServerPacket;
import Controller.Packets.ServerPacketType;
import Controller.utils.ClientPackageListener;
import Controller.utils.ConnectionManager;
import Controller.utils.Logger;
import Model.World;
import View.AppGUI;
import View.MainMenuScene;
import View.RanksScene;
import javafx.application.Application;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements ClientPackageListener {
    private static final Client Instance = new Client();
    private String clientName = "guest";
    private World world;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private ClientPacket handShakingPackekt;

    private Client() {
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

    public void sendPacket(ServerPacket serverPacket) throws Exception {
        if (socket == null) {
            initSocket();
            if (serverPacket.getPacketType() == ServerPacketType.SIGN_UP) {
                clientName = (String) serverPacket.getArgument(0);
            }
        }
        serverPacket.setFromMassage(clientName);
        objectOutputStream.writeObject(serverPacket);
    }

    public void initSocket() throws IOException {
        socket = new Socket("127.0.0.1", ConnectionManager.MAIN_SERVER_PORT);
        Logger.log("connected to server");
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        new Thread(() -> {
            try {
                ClientPacket clientPacket = (ClientPacket) objectInputStream.readObject();
                receive(clientPacket);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassCastException | ClassNotFoundException e) {
                System.err.println("invalid packet");
            }
        }).start();
    }

    @Override
    public void receive(ClientPacket clientPacket) {
        Logger.log(clientPacket.toString());
        switch (clientPacket.getPacketType()) {
            case ERROR_MASSAGE:
                // TODO: 6/1/2018
                break;
            case PROFILES:
                handShakingPackekt = clientPacket;
                break;
            case SUCCESSFUL_LOGIN:
                clientName = (String) clientPacket.getArgument(0);
                handShakingPackekt = clientPacket;
                break;
            case WAITING_GAMES:

        }
    }

    public ClientPacket getHandShakingPacket() {
        while (true)
            if (handShakingPackekt != null) {
                ClientPacket ans = handShakingPackekt;
                handShakingPackekt = null;
                return ans;
            } else {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

    }
}
