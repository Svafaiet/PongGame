package Model.Pong.Ball;

import Model.Pong.BoardProperties;
import Model.Pong.GoalKeeper.GoalKeeper;
import Model.Pong.Location;

import java.io.Serializable;
import java.util.Random;

import static java.lang.Math.*;

public class Ball implements Serializable {
    public final double INITIAL_HORIZONTAL_DIR;
    public final double INITIAL_VERTICAL_DIR;
    public final double INITIAL_SPEED_SIZE = 0.6;
    public final double MAX_SPEED = 6;
    {
        double randomAngle = random()%(PI/3) * (2*(random()%2)-1) ;
        INITIAL_HORIZONTAL_DIR = cos(randomAngle);
        INITIAL_VERTICAL_DIR = sin(randomAngle);
    }

    public static final int DEFAULT_BALL_RADIUS = 7;
    public static final double SPEED_ADD_RATE = 0.001;

    public Ball(BoardProperties boardProperties) {
        ballCircle = new BallCircle();
        ballCircle.setCenterX(boardProperties.getWidth() / 2);
        ballCircle.setCenterY(boardProperties.getHeight() / 2);
        ballCircle.setRadius(DEFAULT_BALL_RADIUS);

        speed = new Speed(INITIAL_HORIZONTAL_DIR, INITIAL_VERTICAL_DIR, INITIAL_SPEED_SIZE);

        ballType = BallType.CLASSIC;

    }

    public Ball(BoardProperties boardProperties, BallType ballType) {
        ballCircle = new BallCircle();
        ballCircle.setCenterX(boardProperties.getWidth() / 2);
        ballCircle.setCenterY(boardProperties.getHeight() / 2);
        ballCircle.setRadius(DEFAULT_BALL_RADIUS);

        speed = new Speed(INITIAL_HORIZONTAL_DIR, INITIAL_VERTICAL_DIR, INITIAL_SPEED_SIZE);

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
        return abs((boardProperties.getWidth() / 2) - ballCircle.getCenterX());

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
        speed.add(new Speed(otherThingSpeed.getHorizontalDir() , otherThingSpeed.getVerticalDir(), otherThingSpeed.getSize()/3));
    }

    public void update() {
        ballCircle.setCenterX(ballCircle.getCenterX() + speed.getHorizontalSpeed());
        ballCircle.setCenterY(ballCircle.getCenterY() + speed.getVerticalSpeed());
        speed.setSize(min(speed.getSize() + SPEED_ADD_RATE, MAX_SPEED));
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

    public boolean isOutOfBoard(BoardProperties boardProperties){
        return !Location.isInRange(ballCircle.getCenterX(), 0, boardProperties.getWidth());
    }

    public void setBallCircle(BallCircle ballCircle) {
        this.ballCircle = ballCircle;
    }
}
