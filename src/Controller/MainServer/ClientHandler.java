package Controller.MainServer;

import Controller.GameSession;
import Controller.Packets.ClientPacket;
import Controller.Packets.ClientPacketType;
import Controller.Packets.ServerPacket;
import Controller.Server;
import Controller.utils.Logger;
import Controller.utils.ServerPackageListener;
import Model.Exceptions.DuplicateGameException;
import Model.Exceptions.DuplicatePlayerNameException;
import Model.Exceptions.GameNotFoundException;
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
    private GameSession currentGameSession;

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
//                objectInputStream.reset();
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
        Logger.log(serverPacket.toString());
        if (!hasLoggedIn) {
            switch (serverPacket.getPacketType()) {
                case SIGN_UP:
                    try {
                        server.getWorld().addNewProfile(serverPacket.getFromMassage());
                        profile = server.getWorld().getProfile(serverPacket.getFromMassage());
                        send(ClientPacketType.SUCCESSFUL_LOGIN, serverPacket.getFromMassage());
                        profile.setOnline(true);
                        hasLoggedIn = true;
                    } catch (DuplicatePlayerNameException e) {
                        send(ClientPacketType.ERROR_MASSAGE, "Account name already exists");
                    } catch (PlayerNotFoundException e) {
                    }
                    break;
                case LOG_IN:
                    try {
                        profile = server.getWorld().getProfile(serverPacket.getFromMassage());
                        if (profile.isOnline()) {
                            send(ClientPacketType.ERROR_MASSAGE, "Account name has already logged in");
                        } else {
                            send(ClientPacketType.SUCCESSFUL_LOGIN, serverPacket.getFromMassage());
                            profile.setOnline(true);
                            hasLoggedIn = true;
                        }
                    } catch (PlayerNotFoundException e) {
                        send(ClientPacketType.ERROR_MASSAGE, "Account name not found");
                    }
                    break;
                case GET_RANKS:
                    send(ClientPacketType.PROFILES, server.getWorld().getProfiles());
                    break;
                default:
                    break;
            }
        } else {
            switch (serverPacket.getPacketType()) {
                case LOG_OUT:
                    send(ClientPacketType.SUCCESSFUL_LOGOUT, profile.getName());
                    profile.setOnline(false);
                    profile = null;
                    hasLoggedIn = false;
                    break;
                case GET_RANKS:
                    send(ClientPacketType.PROFILES, server.getWorld().getProfiles());
                    break;
                case GET_AVAILABLE_GAMES:
                    // TODO: 6/1/2018 we must check for gameType:)
                    send(ClientPacketType.WAITING_GAMES, server.getWorld().getWaitingGames());
                    break;
                case MAKE_GAME:
                    try {
                        currentGameSession = server.makeGameSession(this, serverPacket);
                    } catch (DuplicateGameException e) {
                        send(ClientPacketType.ERROR_MASSAGE, "GameName already exists");
                    } catch (PlayerNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case JOIN:
                    try {
                        currentGameSession = server.addToGameSession(this, (String) serverPacket.getArgument(0));
                    } catch (PlayerNotFoundException e) {
                        e.printStackTrace();
                    } catch (GameNotFoundException e) {
                        send(ClientPacketType.ERROR_MASSAGE, "game not found");
                    }

                    break;
                case GAME_ACTION:
                    if (currentGameSession != null) {
                        currentGameSession.handleActions(serverPacket, this);
                    }
                    break;
                default:
                    System.err.println("Invalid serverPacket" + serverPacket);
                    break;
            }
        }
    }

    public synchronized void send(ClientPacketType packetType, Object... args) {
        ClientPacket clientPacket = new ClientPacket(server.getName());
        clientPacket.setPacketType(packetType);
        clientPacket.addElements(args);
        try {
            objectOutputStream.reset();
            objectOutputStream.writeObject(clientPacket);

        } catch (IOException e) {
            // TODO: 6/1/2018 handling reconnecting
        }

    }

    public Profile getProfile() {
        return profile;
    }

    public void endGameSession() {
        currentGameSession = null;
    }
}
