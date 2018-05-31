package Controller.MainServer;

import Controller.Packets.ClientPacket;
import Controller.Packets.ClientPacketType;
import Controller.Packets.ServerPacket;
import Controller.Server;
import Controller.utils.ServerPackageListener;
import Model.Exceptions.DuplicatePlayerNameException;
import Model.Exceptions.PlayerNotFoundException;
import Model.Profile;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable, ServerPackageListener {
    private Server server;
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private boolean hasLoggedIn = false;
    private Profile profile;

    public void handleSocket(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                ServerPacket serverPacket = (ServerPacket) objectInputStream.readObject();
                receive(serverPacket);
            } catch (ClassNotFoundException | ClassCastException e) {
                System.err.println("Invalid Packet from " + socket);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                // FIXME: 5/31/2018
                e.printStackTrace();
            }
        }
    }

    @Override
    public void receive(ServerPacket serverPacket) {
        if (!hasLoggedIn) {
            switch (serverPacket.getPacketType()) {
                case SIGN_UP:
                    try {
                        server.getWorld().addNewProfile(serverPacket.getFromMassage());
                        send(ClientPacketType.SUCCESSFUL_LOGIN, serverPacket.getFromMassage());
                        hasLoggedIn = true;
                    } catch (DuplicatePlayerNameException e) {
                        send(ClientPacketType.ERROR_MASSAGE, "Account name already exists");
                    }
                    break;
                case LOG_IN:
                    try {
                        profile = server.getWorld().getProfile(serverPacket.getFromMassage());
                        send(ClientPacketType.SUCCESSFUL_LOGIN, serverPacket.getFromMassage());
                        hasLoggedIn = true;
                    } catch (PlayerNotFoundException e) {
                        send(ClientPacketType.ERROR_MASSAGE, "Account name not found");
                    }
                    break;
                case GET_RANKS:
                    send(ClientPacketType.PROFILES, server.getWorld().getProfiles().toArray());
                    break;
                default:
                    break;
            }
        } else {
            switch (serverPacket.getPacketType()) {
                case LOG_OUT:
                    profile = null;
                    hasLoggedIn = true;
                    break;
                case GET_RANKS:
                    send(ClientPacketType.PROFILES, server.getWorld().getProfiles().toArray());
                    break;
                case GET_AVAILABLE_GAMES:
                    // TODO: 6/1/2018 we must check for gameType:)
                    send(ClientPacketType.WAITING_GAMES, server.getWorld().getWaitingGames());
                    break;
                case JOIN:

                    break;
                case GAME_ACTION:

                    break;
                default:
                    System.err.println("Invalid serverPacket" + serverPacket);
                    break;
            }
        }
    }

    public void send(ClientPacketType packetType, Object... args) {
        ClientPacket clientPacket = new ClientPacket(server.getName());
        clientPacket.setPacketType(packetType);
        clientPacket.addElements(args);
        try {
            objectOutputStream.writeObject(clientPacket);
        } catch (IOException e) {
            // TODO: 6/1/2018 handling reconnecting
        }

    }
}
