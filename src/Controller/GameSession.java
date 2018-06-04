package Controller;

import Controller.MainServer.ClientHandler;
import Controller.Packets.ClientPacketType;
import Controller.Packets.ServerPacket;
import Model.Game;
import Model.GameType;
import Model.WaitingGame;

import java.util.ArrayList;

public class GameSession {
    private ArrayList<ClientHandler> players = new ArrayList<>();
    private Game game;
    private WaitingGame waitingGame;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public WaitingGame getWaitingGame() {
        return waitingGame;
    }

    public void setWaitingGame(WaitingGame waitingGame) {
        this.waitingGame = waitingGame;
    }

    public ArrayList<ClientHandler> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<ClientHandler> players) {
        this.players = players;
    }


    public void startGame() {
        game = waitingGame.getGame();
        for (ClientHandler clientHandler : players) {
            clientHandler.send(ClientPacketType.GAME_STARTED, game.getSaveName(), game);
            sendGameProperties(clientHandler);
        }
    }

    private void sendGameProperties(ClientHandler clientHandler) {
        new Thread(() -> {
            while (!game.getGameLogic().isGameFinished()) {
                clientHandler.send(ClientPacketType.GAME_PROPERTIES, game.getSaveName(), game.getGameLogic().getGamePacket());
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    public void getGameProperties(ServerPacket serverPacket, ClientHandler clientHandler) {
        if ((GameType) serverPacket.getArgument(0) == game.getGameLogic().getGameType()) {
            int i = players.indexOf(clientHandler);
            game.getGameLogic().handleCommands(i, serverPacket.getArgument(1));
        }
    }
}
