package View.PongGUI;

import Controller.Packets.ServerPacket;
import Controller.Packets.ServerPacketType;
import Model.Game;
import Model.GameMode;
import Model.Pong.Ball.Ball;
import Model.Pong.GoalKeeper.GoalKeeper;
import Model.Pong.PongLogic;
import Model.Pong.PongPlayer;
import View.AppGUI;
import View.utils.BarScene;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.*;


public class PongScene extends BarScene {
    public static final int PONG_WIDTH = 640;
    public static final int PONG_HEIGHT = 360;
    public static final int BAR_HEIGHT = 40;
    public static final int BORDER_SIZE = 5;
    private static double xMultiplier = 1;
    private static double yMultiplier = 1;

    private final Game pongGame;

    //mainPart
    private Rectangle goalKeeper1 = new Rectangle();
    private Rectangle goalKeeper2 = new Rectangle();
    private Circle ball = new Circle();

    //bar
    private Text chronometer = new Text("0");
    private Text score1 = new Text("0");
    private Text score2 = new Text("0");

    private Set<KeyCode> pressedKeys;
    private Map<KeyCode, String> keyMeaning;

    ArrayList<AnimationTimer> animationTimers = new ArrayList<>();

    public PongScene(Game game) {
        super(new Group(), PONG_WIDTH + 2 * BORDER_SIZE, PONG_HEIGHT + BAR_HEIGHT + 2 * BORDER_SIZE, Color.BLACK);
        this.pongGame = game;
        Group root = getMainPart();
        setBar();
        root.getChildren().add(new Rectangle(PONG_WIDTH + 2 * BORDER_SIZE, PONG_HEIGHT + 2 * BORDER_SIZE));

        setupKeyEvents();
        handleAnimationTimers();
        root.getChildren().addAll(ball, goalKeeper1, goalKeeper2);
    }

    private void handleAnimationTimers() {
        AnimationTimer ballTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                drawBall(getPongLogic().getBall());
            }
        };
        AnimationTimer goalKeeperTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //Drawing GoalKeepers
                drawGoalKeeper(getPongLogic().getGoalKeeper1(), goalKeeper1);
                drawGoalKeeper(getPongLogic().getGoalKeeper2(), goalKeeper2);
            }
        };
        AnimationTimer informationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                drawInformation();

                if (getPongLogic().isGameFinished()) {
                    stopAnimationTimers();
                    AppGUI.setStageScene(new PongMainMenuScene());
                    AppGUI.getGameStage().show();
                }

            }
        };
        AnimationTimer keyTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                handleKeys();
            }
        };
        animationTimers.add(ballTimer);
        animationTimers.add(goalKeeperTimer);
        animationTimers.add(informationTimer);
        animationTimers.add(keyTimer);
        resumeAnimationTimers();
    }
    private void stopAnimationTimers(){
        for(AnimationTimer animationTimer : animationTimers) {
            animationTimer.stop();
        }
    }
    private void resumeAnimationTimers(){
        for(AnimationTimer animationTimer : animationTimers) {
            animationTimer.start();
        }
    }

    private PongLogic getPongLogic() {
        return (PongLogic) pongGame.getGameLogic();
    }

    public void setBar() {
        getBar().setMaxHeight(40);
        getBar().setMaxWidth(PONG_WIDTH + 2 * BORDER_SIZE);
        getBar().setStyle("-fx-border-color: white;");
        getBar().setAlignment(Pos.CENTER);
        getBar().setPadding(new Insets(0, 15, BORDER_SIZE, 15));
        score1.setFill(Color.color((double) 0x19 / 256, (double) 0xfb / 256, (double) 0xff / 256));
        score2.setFill(Color.RED);
        chronometer.setFill(Color.WHITE);
        score1.setFont(new Font(30));
        score2.setFont(new Font(30));
        chronometer.setFont(new Font(26));


        Region region1 = new Region();
        Region region2 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        HBox.setHgrow(region2, Priority.ALWAYS);
        getBar().getChildren().addAll(score1, region1, chronometer, region2, score2);
    }

    private void setupKeyEvents() {
        pressedKeys = new HashSet<>();
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                pressedKeys.add(event.getCode());
            }
        });

        this.setOnKeyReleased(event -> {
            pressedKeys.remove(event.getCode());
        });

        keyMeaning = new HashMap<>();
        if(pongGame.getGameMode().equals(GameMode.SINGLE_PLAYER)) {
            keyMeaning.put(KeyCode.P, "PAUSE");
            keyMeaning.put(KeyCode.UP, "UP_GOALKEEPER2");
            keyMeaning.put(KeyCode.DOWN, "DOWN_GOALKEEPER2");
            keyMeaning.put(KeyCode.W, "UP_GOALKEEPER1");
            keyMeaning.put(KeyCode.S, "DOWN_GOALKEEPER1");
        } else {
            keyMeaning.put(KeyCode.P, "PAUSE_MULTI_PLAYER");
            keyMeaning.put(KeyCode.UP, "UP_GOALKEEPER_MULTI_PLAYER");
            keyMeaning.put(KeyCode.DOWN, "DOWN_GOALKEEPER_MULTI_PLAYER");
        }


    }

    private void handleKeys() {
        for (KeyCode keyCode : pressedKeys) {
            sendCommand(keyCode);
        }
    }

    private void sendCommand(KeyCode keyCode) {
        // FIXME: 5/29/2018 SINGLE_PLAYER_ONLY
        if (keyMeaning.keySet().contains(keyCode)) {
            switch (keyMeaning.get(keyCode)) {
                case "PAUSE":
                    getPongLogic().pauseOrResume();
                    break;
                case "UP_GOALKEEPER1":
                    getPongLogic().moveGoalKeeperUp(1);
                    break;
                case "DOWN_GOALKEEPER1":
                    getPongLogic().moveGoalKeeperDown(1);
                    break;
                case "UP_GOALKEEPER2":
                    getPongLogic().moveGoalKeeperUp(2);
                    break;
                case "DOWN_GOALKEEPER2":
                    getPongLogic().moveGoalKeeperDown(2);
                    break;
                case "PAUSE_MULTI_PLAYER":
                    AppGUI.sendPacket(ServerPacketType.PONG_ACTION, "PAUSE");
                    break;
                case "UP_GOALKEEPER_MULTI_PLAYER":
                    AppGUI.sendPacket(ServerPacketType.PONG_ACTION, "UP");
                    break;
                case "DOWN_GOALKEEPER_MULTI_PLAYER":
                    AppGUI.sendPacket(ServerPacketType.PONG_ACTION, "DOWN");
                    break;


            }
        }
    }

    private void drawGoalKeeper(GoalKeeper goalKeeper, Rectangle rectangle) {
        switch (goalKeeper.getGoalKeeperType()) {
            case CLASSIC:
                drawClassicGoalKeeper(goalKeeper, rectangle);
                break;
            default:
                throw new RuntimeException("unhandled goalkeeper Type");
        }

    }

    private void drawClassicGoalKeeper(GoalKeeper goalKeeper, Rectangle rectangle) {

        switch (goalKeeper.getSide()) {
            case LEFT:
                rectangle.setFill(Color.color((double) 0x19 / 256, (double) 0xfb / 256, (double) 0xff / 256));
                break;
            case RIGHT:
                rectangle.setFill(Color.RED);
                break;
            default:
        }
        rectangle.setHeight(goalKeeper.getRectangle().getHeight());
        rectangle.setWidth(goalKeeper.getRectangle().getWidth());
        rectangle.setX(castLogicalXToGraphical(goalKeeper.getRectangle().getCornerX()));
        rectangle.setY(castLogicalYToGraphical(goalKeeper.getRectangle().getCornerY()));
        rectangle.setArcHeight(15);
        rectangle.setArcWidth(5);
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
        this.ball.setCenterX(castLogicalXToGraphical(ball.getBallCircle().getCenterX()));
        this.ball.setCenterY(castLogicalYToGraphical(ball.getBallCircle().getCenterY()));
        this.ball.setRadius(ball.getBallCircle().getRadius());
        this.ball.setFill(Color.WHITE);
    }

    private void drawInformation() {
        score1.setText("" + ((PongPlayer) getPongLogic().getPlayers().get(0)).getScore());
        score2.setText("" + ((PongPlayer) getPongLogic().getPlayers().get(1)).getScore());
        // FIXME: 5/29/2018 singlePlayer only
        long time = getPongLogic().getTime();
        chronometer.setText(String.format("%d:%02d:%02d", (time/60000), ((time/1000)%60), (time/10)%100));
    }

    private double castLogicalXToGraphical(double x) {
        return BORDER_SIZE + x * xMultiplier;
    }

    private double castLogicalYToGraphical(double y) {
        return BORDER_SIZE + y * yMultiplier;
    }
}
