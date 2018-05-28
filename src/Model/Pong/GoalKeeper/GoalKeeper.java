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

    private Rectangle rectangle;
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


    public Rectangle getRectangle() {
        return rectangle;
    }

    public Side getSide() {
        return side;
    }

    public boolean isAtRight(Ball ball) {
        return Location.isInRange(ball.getBallCircle().getCenterY(),
                (rectangle.getY() - rectangle.getHeight()) / 2,
                (rectangle.getY() + rectangle.getHeight()) / 2)
                &&
                Location.isInRange(ball.getBallCircle().getCenterX(),
                        rectangle.getX(),
                        (rectangle.getX() + rectangle.getWidth()) / 2);

    }

    public boolean isAtLeft(Ball ball) {
        return Location.isInRange(ball.getBallCircle().getCenterY(),
                (rectangle.getY() - rectangle.getHeight()) / 2,
                (rectangle.getY() + rectangle.getHeight()) / 2)
                &&
                Location.isInRange(ball.getBallCircle().getCenterX(),
                        (rectangle.getX() - rectangle.getWidth()) / 2,
                        rectangle.getX());
    }

    public Speed getSpeed() {
        return new Speed(0, 0);
    }
}
