package Controller.MainServer;

import Controller.Packets.ServerPacket;
import Controller.utils.ConnectionManager;
import Controller.utils.ServerPackageListener;
import Model.Exceptions.DuplicateGameException;
import Model.Exceptions.DuplicatePlayerNameException;
import Model.Exceptions.PlayerNotFoundException;
import Model.GameMode;
import Model.GameType;
import Model.World;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class MainServer{
    private static MainServer instance = new MainServer(ConnectionManager.MAIN_SERVER_PORT);
    private int port;
    private ServerSocket serverSocket;
    private Set<ClientHandler> clientHandlers;
    private World world = new World();

    private MainServer(int port) {
        this.port = port;
    }

    public static MainServer getInstance() {
        return instance;
    }

    public World getWorld() {
        return world;
    }

    public static void main(String[] args) throws IOException {
        MainServer mainServer = getInstance();
        mainServer.start();
        mainServer.listenToClients();
    }

    public void start() {
        try {
            setup();
        } catch (IOException e) {
            System.err.println("Server already Running!");
        }
        try {
            listenToClients();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void listenToClients() throws IOException {
        new Thread(() -> {
            clientHandlers = new HashSet<>();
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler();
                    clientHandlers.add(clientHandler);
                    clientHandler.handleSocket(world, socket);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void setup() throws IOException {
        serverSocket = new ServerSocket(port);
        System.err.println("ServerStarted");
    }


}
