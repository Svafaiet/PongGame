package View.PongGUI;

import Model.Pong.Ball.Ball;
import Model.Pong.GoalKeeper.GoalKeeper;
import Model.Pong.PongLogic;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class PongScene extends Scene {
    public static final int PONG_WIDTH = 1280;
    public static final int PONG_HEIGHT = 720;

    private final PongLogic pongLogic;

    public PongScene(PongLogic pongLogic) {
        super(new Group(), PONG_WIDTH, PONG_HEIGHT, Color.BLACK);
        this.pongLogic = pongLogic;
        Group root = (Group) this.getRoot();
        root.getChildren().clear();

        //Drawing GoalKeepers
        drawGoalKeeper(pongLogic.getGoalKeeper1());
        drawGoalKeeper(pongLogic.getGoalKeeper2());

        //Drawing Ball
        drawBall(pongLogic.getBall());
    }

    private void drawGoalKeeper(GoalKeeper goalKeeper) {
        switch (goalKeeper.getGoalKeeperType()) {
            case CLASSIC:
                drawClassicGoalKeeper(goalKeeper);
                break;
            default:
                throw new RuntimeException("unhandled goalKeeprType");
        }

    }

    private void drawClassicGoalKeeper(GoalKeeper goalKeeper) {
        switch (goalKeeper.getSide()) {
            case LEFT:
                goalKeeper.getRectangle().setFill(Color.BLUE);
                break;
            case RIGHT:
                goalKeeper.getRectangle().setFill(Color.RED);
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
        ball.getBallCircle().setFill(Color.WHITE);
    }

}
