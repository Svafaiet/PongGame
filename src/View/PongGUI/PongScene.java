package View.PongGUI;

import Model.Pong.Ball.Ball;
import Model.Pong.GoalKeeper.GoalKeeper;
import Model.Pong.PongLogic;
import View.AppGUI;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Screen;


public class PongScene extends Scene {
    public static final int PONG_WIDTH = 640;
    public static final int PONG_HEIGHT = 360;
    private static double xMultiplier = 1;
    private static double yMultiplier = 1;

    private final PongLogic pongLogic;

    private Rectangle goalKeeper1 = new Rectangle();
    private Rectangle goalKeeper2 = new Rectangle();
    private Circle ball = new Circle();

    public PongScene(PongLogic pongLogic) {
        super(new Group(), PONG_WIDTH, PONG_HEIGHT, Color.BLACK);
        this.pongLogic = pongLogic;
        Group root = (Group) this.getRoot();
        root.getChildren().add(new Rectangle(PONG_WIDTH, PONG_HEIGHT));

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
                goalKeeper1.setHeight(goalKeeper.getRectangle().getHeight()*yMultiplier);
                goalKeeper1.setWidth(goalKeeper.getRectangle().getWidth()*xMultiplier);
                goalKeeper1.setX(goalKeeper.getRectangle().getCenterX()*xMultiplier);
                goalKeeper1.setY(goalKeeper.getRectangle().getCenterY()*yMultiplier);
                break;
            case RIGHT:
                goalKeeper2.setFill(Color.RED);
                goalKeeper2.setHeight(goalKeeper.getRectangle().getHeight()*yMultiplier);
                goalKeeper2.setWidth(goalKeeper.getRectangle().getWidth()*xMultiplier);
                goalKeeper2.setX(goalKeeper.getRectangle().getCenterX()*xMultiplier);
                goalKeeper2.setY(goalKeeper.getRectangle().getCenterY()*yMultiplier);
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
        this.ball.setFill(Color.WHITE);
    }

}
