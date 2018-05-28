package Controller;

import Controller.Packets.ServerPacket;
import Controller.utils.ConnectionManager;
import Controller.utils.ServerMassageListener;
import Model.Exceptions.DuplicateGameException;
import Model.Exceptions.DuplicatePlayerNameException;
import Model.Exceptions.PlayerNotFoundException;
import Model.GameType;
import Model.Profile;
import Model.World;
import com.sun.xml.internal.bind.v2.TODO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainServer implements ServerMassageListener {
    private static MainServer instance = new MainServer(ConnectionManager.MAIN_SERVER_PORT);
    private int port;
    private ServerSocket serverSocket;
    private Map<Socket, Profile> socketProfileMap = new HashMap<>();
    private Map<Profile, Socket> profileSocketMap = new HashMap<>();
    private World world = new World();

    private MainServer(int port) {
        this.port = port;
    }

    public static MainServer getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        MainServer mainServer = getInstance();
        mainServer.start();

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
                    Socket client = serverSocket.accept();
                    ObjectInputStream objectInputStream = new ObjectInputStream(client.getInputStream());
                    ServerPacket serverPacket;
                    try {
                        serverPacket = (ServerPacket) objectInputStream.readObject();
                    } catch (ClassNotFoundException e) {
                        // TODO: 5/28/2018 send Massage
                        client.close();
                        objectInputStream.close();
                        continue;
                    }
                    if(!world.hasProfile(serverPacket.getFromMassage())) {
                        try {
                            world.addNewProfile(serverPacket.getFromMassage());
                            profileSocketMap.put(world.getProfile(serverPacket.getFromMassage()), client);
                            socketProfileMap.put(client, world.getProfile(serverPacket.getFromMassage()));
                        } catch (DuplicatePlayerNameException|PlayerNotFoundException e) {
                        }
                    } else {
                        try {
                            Profile profile = world.getProfile(serverPacket.getFromMassage());
                            Socket socket = profileSocketMap.get(profile);
                            if (socket.isConnected()) {
                                socket.close();
                            }
                            socketProfileMap.remove(socket);
                            socketProfileMap.put(client, profile);
                            profileSocketMap.replace(profile, client);
                        } catch (PlayerNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
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

    @Override
    public void receive(ServerPacket serverPacket) {
        switch (serverPacket.getPacketType()) {
            case "ADD_PROFILE":
                try {
                    world.addNewProfile(serverPacket.getFirstArgument());
                } catch (DuplicatePlayerNameException e) {
                    // TODO: 5/24/2018
                }
                break;
            case "START":
                try {
                    world.makeNewGame(serverPacket.getFromMassage(), serverPacket.getSecondArgument(), GameType.valueOf(serverPacket.getFirstArgument()));
                } catch (PlayerNotFoundException e) {
                    e.printStackTrace();
                } catch (DuplicateGameException e) {
                    // TODO: 5/24/2018
                }
                break;
            case "JOIN":
                try {
                    world.addPlayerToGame(serverPacket.getFromMassage(), serverPacket.getFirstArgument());
                } catch (PlayerNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "PONG_ACTION":
                if (serverPacket.getFirstArgument().equals("UP")) {

                    // TODO: 5/24/2018
                } else if (serverPacket.getFirstArgument().equals("DOWN")) {
                    // TODO: 5/24/2018
                } else {
                    //INVALID packet todo
                }
                break;
            default:
                    //INVALID packet todo
        }
    }
}
