package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class WaitingGame implements Serializable {
    private GameMaker gameMaker;
    private ArrayList<Profile> profiles = new ArrayList<>();
    private String saveName;
    private GameMode gameMode;
    private Game game;

    public GameMaker getGameMaker() {
        return gameMaker;
    }

    public ArrayList<Profile> getProfiles() {
        return profiles;
    }

    public Game getGame() {
        return game;
    }

    public String getSaveName() {
        return saveName;
    }

    public WaitingGame(String saveName, GameMode gameMode, GameMaker gameMaker){
        this.saveName = saveName;
        this.gameMaker = gameMaker;
        this.gameMode = gameMode;
    }

    public void addProfile(Profile profile) {
        if(!isFull()) {
            profiles.add(profile);
        }
    }

    public boolean isFull() {
        return gameMaker.getPlayerCount() <= profiles.size();
    }

    public Game makeGame(){
        game = new Game(gameMaker, saveName, gameMode, profiles);
        return game;
    }
}
