package Controller.MainServer;

import Controller.Packets.ServerPacket;
import Controller.Server;
import Controller.utils.ConnectionManager;
import Controller.utils.Logger;
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

public class MainServer extends Server {
    private static MainServer instance = new MainServer(ConnectionManager.MAIN_SERVER_PORT);

    private MainServer(int port) {
        super(port);
        setName("MainServer");
    }

    public static MainServer getInstance() {
        return instance;
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
            while (true) {
                try {
                    Socket socket = getServerSocket().accept();
                    Logger.log("client connected");
                    ClientHandler clientHandler = new ClientHandler();
                    getClientHandlers().add(clientHandler);
                    clientHandler.handleSocket(this, socket);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void setup() throws IOException {
        setServerSocket(new ServerSocket(getPort()));
        System.err.println("ServerStarted");
    }


}
