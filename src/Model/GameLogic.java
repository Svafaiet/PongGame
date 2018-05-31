package Model;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class GameLogic implements Serializable {
    private ArrayList<Player> players = new ArrayList<>();
    private GameType gameType;

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public abstract boolean isGameFinished();
}
