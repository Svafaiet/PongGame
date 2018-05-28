package Model.Pong.Ball;

import Model.Pong.BoardProperties;
import Model.Pong.GoalKeeper.GoalKeeper;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.Serializable;

public class Ball implements Serializable {
    public static final int INITIAL_HORIZONTAL_SPEED = 10;
    public static final int INITIAL_VERTICAL_SPEED = 0;
    public static final int DEFAULT_BALL_RADIUS = 7;
    public static final double SPEED_ADD_RATE = 1;

    public Ball(BoardProperties boardProperties) {
        ballCircle = new BallCircle();
        ballCircle.setCenterX(boardProperties.getWidth() / 2);
        ballCircle.setCenterY(boardProperties.getHeight() / 2);
        ballCircle.setRadius(DEFAULT_BALL_RADIUS);

        speed = new Speed(INITIAL_HORIZONTAL_SPEED, INITIAL_VERTICAL_SPEED);

        ballType = BallType.CLASSIC;

    }

    public Ball(BoardProperties boardProperties, BallType ballType) {
        ballCircle = new BallCircle();
        ballCircle.setCenterX(boardProperties.getWidth() / 2);
        ballCircle.setCenterY(boardProperties.getHeight() / 2);
        ballCircle.setRadius(DEFAULT_BALL_RADIUS);

        speed = new Speed(INITIAL_HORIZONTAL_SPEED, INITIAL_VERTICAL_SPEED);

        this.ballType = ballType;
    }

    private BallCircle ballCircle;
    private Speed speed;
    private BallType ballType;
    boolean hasBallChanged = true;

    public BallCircle getBallCircle() {
        return ballCircle;
    }

    public Speed getSpeed() {
        return speed;
    }

    public double getDistanceFromCenter(BoardProperties boardProperties) {
        return Math.abs((boardProperties.getWidth() / 2) - ballCircle.getCenterX());

    }

    public BallType getBallType() {
        return ballType;
    }

    public void reflectBall(Speed otherThingSpeed, boolean isHorizontal) {
        if (isHorizontal) {
            speed.reverseHorizontalSpeed();
        } else {
            speed.reverseVerticalSpeed();
        }
        changeSpeed(otherThingSpeed);
    }

    public void changeSpeed(Speed otherThingSpeed) {
        speed.add(new Speed(otherThingSpeed.getHorizontalSpeed() / 3, otherThingSpeed.getVerticalSpeed() / 3));
    }

    public void update() {
        ballCircle.setCenterX(ballCircle.getCenterX() + speed.getHorizontalSpeed());
        ballCircle.setCenterY(ballCircle.getCenterY() + speed.getVerticalSpeed());
        speed.setSize(speed.getSize() + SPEED_ADD_RATE);
        if (hasBallChanged) {
            hasBallChanged = false;
            switch (ballType) {
                case CLASSIC:
                    changeBallToClassic();
                    break;
                default:
                    throw new RuntimeException("unhandled BallType in ball update");
            }
        }
    }

    private void changeBallToClassic() {
        ballCircle.setRadius(DEFAULT_BALL_RADIUS);
        // FIXME: 5/28/2018 animationTimer
    }

    public void reflectFromGoalKeeper(GoalKeeper goalKeeper) {
        if (goalKeeper.isAtRight(this)) {
            if (speed.getHorizontalSpeed() < 0)
                reflectBall(goalKeeper.getSpeed(), true);
            else
                changeSpeed(goalKeeper.getSpeed());
        }
        if (goalKeeper.isAtLeft(this)) {
            if (speed.getHorizontalSpeed() < 0) {
                changeSpeed(goalKeeper.getSpeed());
            } else {
                reflectBall(goalKeeper.getSpeed(), true);
            }
        }

    }


}
