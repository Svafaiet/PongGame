package Controller.Packets;

import Model.Pong.Ball.BallCircle;
import Model.Pong.GoalKeeper.GoalKeeperRectangle;
import Model.Pong.PongLogic;

import java.io.Serializable;

public class PongPacket extends GamePacket implements Serializable {
    private GoalKeeperRectangle goalKeeper1Rectangle;
    private GoalKeeperRectangle goalKeeper2Rectangle;
    private BallCircle ballCircle;

    private long time;

    public PongPacket (PongLogic pongLogic) {
        super(pongLogic.getPlayers());

        goalKeeper1Rectangle = pongLogic.getGoalKeeper1().getRectangle();
        goalKeeper2Rectangle = pongLogic.getGoalKeeper2().getRectangle();
        ballCircle = pongLogic.getBall().getBallCircle();

        time = pongLogic.getTime();

    }

    public GoalKeeperRectangle getGoalKeeper1Rectangle() {
        return goalKeeper1Rectangle;
    }

    public GoalKeeperRectangle getGoalKeeper2Rectangle() {
        return goalKeeper2Rectangle;
    }

    public BallCircle getBallCircle() {
        return ballCircle;
    }

    public long getTime() {
        return time;
    }
}
