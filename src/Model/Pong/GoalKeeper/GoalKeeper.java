package Model.Pong.GoalKeeper;

import Model.Pong.Ball.Ball;
import Model.Pong.Ball.Speed;
import Model.Pong.Location;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class GoalKeeper implements Serializable {
    public static final double DEFAULT_GOALKEEPER_HEIGHT = 30;
    public static final double DEFAULT_GOALKEEPER_WIDTH = 8;
    public static final GoalKeeperType DEFAULT_GOALKEEPER_TYPE = GoalKeeperType.CLASSIC;

    private GoalKeeperRectangle rectangle;
    private GoalKeeperType goalKeeperType;
    private Side side;

    public GoalKeeper(Side side) {
        goalKeeperType = DEFAULT_GOALKEEPER_TYPE;
        rectangle.setHeight(DEFAULT_GOALKEEPER_HEIGHT);
        rectangle.setWidth(DEFAULT_GOALKEEPER_WIDTH);
        this.side = side;
    }

    public void changeGoalKeeper(int gameHeightMultiplier, int gameWidthMultiplier) {
        rectangle.setHeight(DEFAULT_GOALKEEPER_HEIGHT * gameHeightMultiplier);
        rectangle.setWidth(DEFAULT_GOALKEEPER_WIDTH * gameWidthMultiplier);
    }

    public GoalKeeperType getGoalKeeperType() {
        return goalKeeperType;
    }

    public void changeGoalKeeper(GoalKeeperType goalKeeperType){
        this.goalKeeperType = goalKeeperType;
    }


    public GoalKeeperRectangle getRectangle() {
        return rectangle;
    }

    public Side getSide() {
        return side;
    }

    public boolean isAtRight(Ball ball) {
        return Location.isInRange(ball.getBallCircle().getCenterY(),
                (rectangle.getCenterY() - rectangle.getHeight()) / 2,
                (rectangle.getCenterY() + rectangle.getHeight()) / 2)
                &&
                Location.isInRange(ball.getBallCircle().getCenterX(),
                        rectangle.getCenterX(),
                        (rectangle.getCenterX() + rectangle.getWidth()) / 2);

    }

    public boolean isAtLeft(Ball ball) {
        return Location.isInRange(ball.getBallCircle().getCenterY(),
                (rectangle.getCenterY() - rectangle.getHeight()) / 2,
                (rectangle.getCenterY() + rectangle.getHeight()) / 2)
                &&
                Location.isInRange(ball.getBallCircle().getCenterX(),
                        (rectangle.getCenterX() - rectangle.getWidth()) / 2,
                        rectangle.getCenterX());
    }

    public Speed getSpeed() {
        return new Speed(0, 0);
    }
}
