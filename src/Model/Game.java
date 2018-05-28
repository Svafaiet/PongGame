package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Game implements Serializable {
    private GameLogic gameLogic;
    private String saveName;

    public Game(GameMaker gameMaker, String saveName, ArrayList<Profile> profiles) {
        ArrayList<Player> players = new ArrayList<>();
        for(Profile profile : profiles) {
            players.add(gameMaker.makeNewPlayer(profile));
        }
        gameLogic = gameMaker.makeNewGame(players);
        this.saveName = saveName;
    }

    public String getSaveName() {
        return saveName;
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(saveName, game.saveName);
    }

    public boolean equals(String o) {
        return saveName.equals(o); // TODO: 5/17/2018 ask
    }

}
