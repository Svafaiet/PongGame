package Model;

import Model.Exceptions.DuplicateGameException;
import Model.Exceptions.DuplicatePlayerNameException;
import Model.Exceptions.GameNotFoundException;
import Model.Exceptions.PlayerNotFoundException;
import Model.Pong.PongMaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class World {
    private ArrayList<Profile> profiles = new ArrayList<>();
    private ArrayList<Game> games = new ArrayList<>();
    private ArrayList<WaitingGame> waitingGames = new ArrayList<>();
    private ArrayList<Game> runningGames = new ArrayList<>();

    public Map<GameType, GameMaker> gameMakers = new HashMap<>();
    {
        gameMakers.put(GameType.PONG, new PongMaker());
    }

    //Accessing
    public boolean hasProfile(String playerName) {
        return profiles.contains(playerName);
    }

    public boolean hasGame(String gameName) {
        return games.contains(gameName);
    }

    public ArrayList<Profile> getProfiles() {
        return profiles;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public ArrayList<WaitingGame> getWaitingGames() {
        return waitingGames;
    }

    public ArrayList<Game> getRunningGames() {
        return runningGames;
    }

    public Profile getProfile(String playerName) throws PlayerNotFoundException {
        for(Profile profile : profiles) {
            if (profile.equals(playerName)) {
                return profile;
            }
        }
        throw new PlayerNotFoundException(playerName);
    }

    public Game getRunningGame(String saveName) throws GameNotFoundException {
        for (Game game : runningGames) {
            if(game.equals(saveName)) {
                return game;
            }
        }
        throw new GameNotFoundException(saveName);
    }


    //game and profileManaging
    public void addNewProfile(String playerName) throws DuplicatePlayerNameException {
        if(hasProfile(playerName)) {
            throw new DuplicatePlayerNameException();
        }
        profiles.add(new Profile(playerName));
    }

    public void makeNewGame(String player1Name, String saveName, GameType gameType)
            throws PlayerNotFoundException , DuplicateGameException {
        if(hasGame(saveName)) {
            throw new DuplicateGameException();
        }
        Profile creator = getProfile(player1Name);
        WaitingGame wg = new WaitingGame(saveName, gameMakers.get(gameType)) ;
        wg.addProfile(creator);
        if (wg.isFull()) {
            runningGames.add(wg.makeGame());
        } else {
            waitingGames.add(wg);
        }
    }

    public void addPlayerToGame(String playerName, String gameName) throws PlayerNotFoundException {
        for(WaitingGame waitingGame : waitingGames) {
            if(gameName.equals(waitingGame.getSaveName())) {
                waitingGame.addProfile(getProfile(playerName));
                if(waitingGame.isFull()) {
                    runningGames.add(waitingGame.makeGame());
                    waitingGames.remove(waitingGame);
                    break;
                }
            }
        }
    }

    private void moveGame(String saveName, ArrayList<Game> src, ArrayList<Game> des) throws GameNotFoundException {
        boolean flag = false;
        for(Game game : src) {
            if (game.equals(saveName)) {
                des.add(game);
                src.remove(game);
                flag = true;
                break;
            }
        }
        if(!flag) {
            throw new GameNotFoundException(saveName);
        }
    }

    public void saveGame(String saveName) throws GameNotFoundException {
        moveGame(saveName, runningGames, games);
    }

    public void loadGame(String saveName) throws GameNotFoundException {
        moveGame(saveName, games, runningGames);
    }

    public void endGame(String saveName) throws GameNotFoundException {
        moveGame(saveName, runningGames, new ArrayList<>());
    }
}
