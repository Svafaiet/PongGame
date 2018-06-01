package Controller;

import Controller.MainServer.ClientHandler;
import Model.*;
import Model.Exceptions.DuplicateGameException;
import Model.Exceptions.GameNotFoundException;
import Model.Exceptions.PlayerNotFoundException;

import java.net.ServerSocket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Server {
    private String name;
    private int port;
    private ServerSocket serverSocket;
    private Set<ClientHandler> clientHandlers = new HashSet<>();
    private Set<GameSession> gameSessions = new HashSet<>();
    private Map<String, GameSession> waitingGameSessionHashMap = new HashMap<>();
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

    public GameSession makeGameSession(String firstPlayerName, String saveName, GameType gameType) throws DuplicateGameException, PlayerNotFoundException {
        GameSession gameSession = new GameSession();
        gameSession.setWaitingGame(world.makeNewGame(firstPlayerName, saveName, GameMode.MULTI_PLAYER, gameType));
        gameSessions.add(gameSession);
        waitingGameSessionHashMap.put(gameSession.getWaitingGame().getSaveName(), gameSession);
        return gameSession;
        // FIXME: 6/1/2018 memory lick
    }

    public Set<GameSession> getGameSessions() {
        return gameSessions;
    }

    public void addToGameSession(ClientHandler clientHandler, String gameName) throws PlayerNotFoundException, GameNotFoundException {
        world.addPlayerToGame(clientHandler.getProfile().getName(), gameName);
        GameSession gameSession = waitingGameSessionHashMap.get(gameName);
        if (gameSession.getWaitingGame().isFull()) {
            gameSession.startGame();
        }
    }
}
