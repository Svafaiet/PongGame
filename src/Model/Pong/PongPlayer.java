package Model.Pong;

import Model.Player;

public class PongPlayer extends Player {
    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(){
        score++;
    }

    @Override
    public void win() {
        getPlayerProfile().setWinCount(getPlayerProfile().getWinCount() + 1);
    }
}
