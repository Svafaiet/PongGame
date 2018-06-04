package Controller;

import Controller.Packets.*;
import Controller.utils.ClientPackageListener;
import Controller.utils.ConnectionManager;
import Controller.utils.Logger;
import Model.Exceptions.DuplicateGameException;
import Model.Exceptions.GameNotFoundException;
import Model.Game;
import Model.World;
import View.AppGUI;
import javafx.application.Application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements ClientPackageListener {
    private static final Client Instance = new Client();
    private String clientName = "guest";
    private World world;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private ClientPacket handShakingPacket;

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

    public synchronized void sendPacket(ServerPacket serverPacket) throws Exception {
        if(socket == null) {
            initSocket();
        }
        if (serverPacket.getPacketType() == ServerPacketType.SIGN_UP || serverPacket.getPacketType() == ServerPacketType.LOG_IN) {
            clientName = (String) serverPacket.getArgument(0);
        }
        serverPacket.setFromMassage(clientName);
        objectOutputStream.reset();
        objectOutputStream.writeObject(serverPacket);
    }

    public void initSocket() throws IOException {
        socket = new Socket("127.0.0.1", ConnectionManager.MAIN_SERVER_PORT);
        Logger.log("connected to server");
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        new Thread(() -> {
            while (true) {
                try {
                    ClientPacket clientPacket = (ClientPacket) objectInputStream.readUnshared();
                    receive(clientPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassCastException | ClassNotFoundException e) {
                    System.err.println("invalid packet");
                }
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
            case SUCCESSFUL_LOGIN:
                clientName = (String) clientPacket.getArgument(0);
                break;
            case SUCCESSFUL_LOGOUT:
                clientName = "guest";
                break;
        }
        if (!clientPacket.getPacketType().isHandShaker()) {
            switch (clientPacket.getPacketType()) {
                case GAME_PROPERTIES:
                    try {
                        Game game = getWorld().getRunningGame((String) clientPacket.getArgument(0));
                        game.getGameLogic().receiveGamePacket((GamePacket) clientPacket.getArgument(1));
                    } catch (GameNotFoundException|ClassCastException e) {
                        e.printStackTrace();
                    }
                    break;
                case GAME_FINISHED:
                    // TODO: 6/4/2018
                    break;
            }

        } else {
            handShakingPacket = clientPacket;
            if(clientPacket.getPacketType().equals(ClientPacketType.GAME_STARTED)) {
                try {
                    world.addGame((Game) clientPacket.getArgument(1));
                } catch (DuplicateGameException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ClientPacket getHandShakingPacket() {
        while (true)
            if (handShakingPacket != null) {
                ClientPacket ans = handShakingPacket;
                handShakingPacket = null;
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
