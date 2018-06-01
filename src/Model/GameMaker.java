package Model;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class GameMaker implements Serializable {
    public abstract GameLogic makeNewGame(ArrayList<Player> players);
    public abstract Player makeNewPlayer(Profile profile);
    public abstract int getPlayerCount();
}
