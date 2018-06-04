package Model.Pong;

import Model.GameLogic;
import Model.GameMaker;
import Model.Player;
import Model.Profile;

import java.io.Serializable;
import java.util.ArrayList;

public class PongMaker extends GameMaker implements Serializable {



    @Override
    public GameLogic makeNewGame(ArrayList<Player> players) {
        PongLogic pongLogic = new PongLogic();
        for(Player player : players) {
            pongLogic.getPlayers().add(player);
        }
        pongLogic.start();
        return pongLogic;
    }

    @Override
    public Player makeNewPlayer(Profile profile) {
        PongPlayer pongPlayer = new PongPlayer();
        pongPlayer.setPlayerProfile(profile);
        pongPlayer.setScore(0);
        return pongPlayer;
    }

    @Override
    public int getPlayerCount() {
        return 2;
    }
}
