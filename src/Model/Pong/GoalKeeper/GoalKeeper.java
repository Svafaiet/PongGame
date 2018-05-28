package Model.Pong.GoalKeeper;

import Model.Pong.Ball.Ball;
import Model.Pong.Ball.Speed;
import Model.Pong.BoardProperties;
import Model.Pong.Location;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class GoalKeeper implements Serializable {
    public static final double DEFAULT_GOALKEEPER_HEIGHT = 66;
    public static final double DEFAULT_GOALKEEPER_WIDTH = 14;
    public static final GoalKeeperType DEFAULT_GOALKEEPER_TYPE = GoalKeeperType.CLASSIC;
    public static final double MOVE_AMOUNT = 2;

    private GoalKeeperRectangle rectangle = new GoalKeeperRectangle();
    private GoalKeeperType goalKeeperType;
    private Side side;

    public GoalKeeper(Side side, BoardProperties boardProperties) {
        goalKeeperType = DEFAULT_GOALKEEPER_TYPE;
        rectangle.setHeight(DEFAULT_GOALKEEPER_HEIGHT);
        rectangle.setWidth(DEFAULT_GOALKEEPER_WIDTH);
        rectangle.setCenterY(boardProperties.getHeight()/2);
        switch (side) {
            case RIGHT:
                rectangle.setCenterX(boardProperties.getWidth() - rectangle.getWidth()/2);
                break;
            case LEFT:
                rectangle.setCenterX(rectangle.getWidth()/2);
                break;
        }

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
                (rectangle.getCenterY() - rectangle.getHeight() / 2),
                (rectangle.getCenterY() + rectangle.getHeight() / 2))
                &&
                Location.isInRange(ball.getBallCircle().getCenterX(),
                        rectangle.getCenterX(),
                        (rectangle.getCenterX() + (rectangle.getWidth() + ball.getBallCircle().getRadius()) / 2));

    }

    public boolean isAtLeft(Ball ball) {
        return Location.isInRange(ball.getBallCircle().getCenterY(),
                (rectangle.getCenterY() - rectangle.getHeight() / 2) ,
                (rectangle.getCenterY() + rectangle.getHeight() / 2) )
                &&
                Location.isInRange(ball.getBallCircle().getCenterX(),
                        (rectangle.getCenterX() - (rectangle.getWidth() + ball.getBallCircle().getRadius()) / 2),
                        rectangle.getCenterX());
    }

    public Speed getSpeed() {
        return new Speed(0, 1, 0);
    }

    public void moveUp(BoardProperties boardProperties) {
        if((rectangle.getCenterY() >= rectangle.getHeight()/2 )) {
        rectangle.setCenterY(rectangle.getCenterY() - MOVE_AMOUNT);
        }
    }

    public void moveDown(BoardProperties boardProperties){
        if((rectangle.getCenterY() <= boardProperties.getHeight() - rectangle.getHeight()/2 )) {
            rectangle.setCenterY(rectangle.getCenterY() + MOVE_AMOUNT);
        }
    }
}
