package Model;

import java.util.ArrayList;

public abstract class GameMaker {
    public abstract GameLogic makeNewGame(ArrayList<Player> players);
    public abstract Player makeNewPlayer(Profile profile);
    public abstract int getPlayerCout();
}
