package Model.Pong;

import Model.GameLogic;
import Model.GameType;
import Model.Pong.Ball.Ball;
import Model.Pong.Ball.BallType;
import Model.Pong.Ball.Speed;
import Model.Pong.GoalKeeper.GoalKeeper;
import Model.Pong.GoalKeeper.GoalKeeperType;
import Model.Pong.GoalKeeper.Side;

public class PongLogic extends GameLogic {
    public static final int DEFAULT_GAME_WIDTH = 640;
    public static final int DEFAULT_GAME_HEIGHT = 360;

    private BoardProperties boardProperties;
    private GoalKeeper goalKeeper1;
    private GoalKeeper goalKeeper2;
    private Ball ball;


    public PongLogic() {
        setGameType(GameType.PONG);

        boardProperties = BoardProperties.DEFAULT_BOARD;

        goalKeeper1 = new GoalKeeper(Side.LEFT, boardProperties);
        goalKeeper2 = new GoalKeeper(Side.RIGHT, boardProperties);

        ball = new Ball(boardProperties);
    }

    public PongLogic(GoalKeeperType goalKeeperType1, GoalKeeperType goalKeeperType2, BallType ballType) {
        setGameType(GameType.PONG);

        boardProperties = BoardProperties.DEFAULT_BOARD;

        goalKeeper1 = new GoalKeeper(Side.LEFT, boardProperties);
        goalKeeper1.changeGoalKeeper(goalKeeperType1);
        goalKeeper2 = new GoalKeeper(Side.RIGHT,boardProperties);
        goalKeeper2.changeGoalKeeper(goalKeeperType2);

        ball = new Ball(boardProperties, ballType);
    }

    public BoardProperties getBoardProperties() {
        return boardProperties;
    }

    public GoalKeeper getGoalKeeper1() {
        return goalKeeper1;
    }

    public GoalKeeper getGoalKeeper2() {
        return goalKeeper2;
    }

    public Ball getBall() {
        return ball;
    }

    public void update() {
        reflectBall();
        ball.update();

    }

    private void reflectBall() {
        if (Location.isInRange(ball.getBallCircle().getCenterX(), 0, boardProperties.getWidth())) {
            ball.reflectBall(new Speed(0, 0), true);
        }
        if (Location.isInRange(ball.getBallCircle().getCenterY(), 0, boardProperties.getHeight())) {
            ball.reflectBall(new Speed(0, 0), false);
        }
        ball.reflectFromGoalKeeper(goalKeeper1);
        ball.reflectFromGoalKeeper(goalKeeper2);
    }
}
