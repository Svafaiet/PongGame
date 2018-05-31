package Model.Pong;

import Model.GameLogic;
import Model.GameType;
import Model.Player;
import Model.Pong.Ball.Ball;
import Model.Pong.Ball.BallType;
import Model.Pong.Ball.Speed;
import Model.Pong.GoalKeeper.GoalKeeper;
import Model.Pong.GoalKeeper.GoalKeeperType;
import Model.Pong.GoalKeeper.Side;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

public class PongLogic extends GameLogic {
    public static final int WIN_SCORE = 5;
    public static final int DEFAULT_GAME_WIDTH = 640;
    public static final int DEFAULT_GAME_HEIGHT = 360;
    public static final int UPDATE_PERIOD = 3;

    private BoardProperties boardProperties;
    private GoalKeeper goalKeeper1;
    private GoalKeeper goalKeeper2;
    private Ball ball;
    private long time = 0;

    private boolean isPaused = false;
    private long lastTimePaused = 0;

    Timer timer = new Timer();

    public PongLogic() {
        setGameType(GameType.PONG);

        setupGame();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 0, UPDATE_PERIOD);
    }

    public void setupGame() {
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
        goalKeeper2 = new GoalKeeper(Side.RIGHT, boardProperties);
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

    public long getTime() {
        return time;
    }

    public void update() {
        if (!isPaused) {
            reflectBall();
            ball.update();
            if (ball.isOutOfBoard(boardProperties)) {
                if (ball.getBallCircle().getCenterX() < 0) {
                    ((PongPlayer) getPlayers().get(1)).addScore();
                } else {
                    ((PongPlayer) getPlayers().get(0)).addScore();
                }
                if (isGameFinished()) {
                    whoIsWinner().win();
                    timer.cancel();
                }
                setupGame();
            }
            time += UPDATE_PERIOD;

        }

    }

    public boolean isGameFinished() {
        for (Player player : getPlayers()) {
            PongPlayer pongPlayer = (PongPlayer) player;
            if (pongPlayer.getScore() >= WIN_SCORE) {
                return true;
            }
        }
        return false;
    }

    private Player whoIsWinner() {
        for (Player player : getPlayers()) {
            PongPlayer pongPlayer = (PongPlayer) player;
            if (pongPlayer.getScore() >= WIN_SCORE) {
                return player;
            }
        }
        return null;
    }

    private void reflectBall() {
//        if (!Location.isInRange(ball.getBallCircle().getCenterX(), 0, boardProperties.getWidth())) {
//            ball.reflectBall(new Speed(0, 1, 0), true);
//        }
        if (!Location.isInRange(ball.getBallCircle().getCenterY(), ball.getBallCircle().getRadius(),
                boardProperties.getHeight() - ball.getBallCircle().getRadius())) {
            ball.reflectBall(new Speed(0, 1, 0), false);
        }
        ball.reflectFromGoalKeeper(goalKeeper1);
        ball.reflectFromGoalKeeper(goalKeeper2);
    }

    public void moveGoalKeeperUp(int goalKeeperNumber) {
        if (!isPaused)
            if (goalKeeperNumber == 1) {
                goalKeeper1.moveUp(boardProperties);
            } else {
                goalKeeper2.moveUp(boardProperties);
            }
    }

    public void moveGoalKeeperDown(int goalKeeperNumber) {
        if (!isPaused)
            if (goalKeeperNumber == 1) {
                goalKeeper1.moveDown(boardProperties);
            } else {
                goalKeeper2.moveDown(boardProperties);
            }
    }

    public void pauseOrResume() {
        if(System.currentTimeMillis() > lastTimePaused + 300) {
            isPaused = !isPaused;
            lastTimePaused  = System.currentTimeMillis();
        }

    }
}
