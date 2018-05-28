package View.PongGUI;

import Model.Pong.Ball.Ball;
import Model.Pong.GoalKeeper.GoalKeeper;
import Model.Pong.PongLogic;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;


public class PongScene extends Scene {
    public static final int PONG_WIDTH = 640;
    public static final int PONG_HEIGHT = 360;

    private final PongLogic pongLogic;

    private Rectangle goalKeeper1 = new Rectangle();
    private Rectangle goalKeeper2 = new Rectangle();
    private Circle ball = new Circle();

    public PongScene(PongLogic pongLogic) {
        super(new Group(), PONG_WIDTH, PONG_HEIGHT, Color.BLACK);
        this.pongLogic = pongLogic;
        Group root = (Group) this.getRoot();
        root.getChildren().clear();

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //Drawing GoalKeepers
                drawGoalKeeper(pongLogic.getGoalKeeper1());
                drawGoalKeeper(pongLogic.getGoalKeeper2());

                //Drawing Ball
                drawBall(pongLogic.getBall());
            }
        };
        animationTimer.start();
        root.getChildren().addAll(ball, goalKeeper1, goalKeeper2);
    }

    private void drawGoalKeeper(GoalKeeper goalKeeper) {
        switch (goalKeeper.getGoalKeeperType()) {
            case CLASSIC:
                drawClassicGoalKeeper(goalKeeper);
                break;
            default:
                throw new RuntimeException("unhandled goalkeeper Type");
        }

    }

    private void drawClassicGoalKeeper(GoalKeeper goalKeeper) {

        switch (goalKeeper.getSide()) {
            case LEFT:
                goalKeeper1.setFill(Color.BLUE);
                goalKeeper1.setX(goalKeeper.getRectangle().getCenterX());
                goalKeeper1.setY(goalKeeper.getRectangle().getCenterY());
                goalKeeper1.setHeight(goalKeeper.getRectangle().getHeight());
                goalKeeper1.setWidth(goalKeeper.getRectangle().getWidth());
                break;
            case RIGHT:
                goalKeeper2.setFill(Color.RED);
                goalKeeper2.setX(goalKeeper.getRectangle().getCenterX());
                goalKeeper2.setY(goalKeeper.getRectangle().getCenterY());
                goalKeeper2.setHeight(goalKeeper.getRectangle().getHeight());
                goalKeeper2.setWidth(goalKeeper.getRectangle().getWidth());
                break;
            default:
        }

    }

    private void drawBall(Ball ball) {
        switch (ball.getBallType()) {
            case CLASSIC:
                drawClassicBall(ball);
                break;
            default:
                throw new RuntimeException("unhandled balltype");
        }
    }

    private void drawClassicBall(Ball ball) {
        this.ball.setCenterX(ball.getBallCircle().getCenterX());
        this.ball.setCenterY(ball.getBallCircle().getCenterY());
        this.ball.setRadius(ball.getBallCircle().getRadius());
        this.setFill(Color.WHITE);
    }

}
