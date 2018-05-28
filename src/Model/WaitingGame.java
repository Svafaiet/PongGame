package Model;

import java.util.ArrayList;

public class WaitingGame {
    private GameMaker gameMaker;
    private ArrayList<Profile> profiles = new ArrayList<>();
    private String saveName;

    public GameMaker getGameMaker() {
        return gameMaker;
    }

    public ArrayList<Profile> getProfiles() {
        return profiles;
    }

    public String getSaveName() {
        return saveName;
    }

    public WaitingGame(String saveName, GameMaker gameMaker){
        this.saveName = saveName;
        this.gameMaker = gameMaker;
    }

    public void addProfile(Profile profile) {
        if(!isFull()) {
            profiles.add(profile);
        }
    }

    public boolean isFull() {
        return gameMaker.getPlayerCout() <= profiles.size();
    }

    public Game makeGame(){
        return new Game(gameMaker, saveName, profiles);
    }
}
