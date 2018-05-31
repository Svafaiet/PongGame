package Controller;

import Controller.MainServer.ClientHandler;
import Model.World;

import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

public class Server {
    private String name;
    private int port;
    private ServerSocket serverSocket;
    private Set<ClientHandler> clientHandlers = new HashSet<>();
    private World world = new World();

    public Server(int port) {
        this.port = port;
    }

    public World getWorld() {
        return world;
    }

    public int getPort() {
        return port;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public Set<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
