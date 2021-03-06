package Controller.Packets;

import Model.Player;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class GamePacket implements Serializable {
    private ArrayList<Player> players;

    public GamePacket(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

}
