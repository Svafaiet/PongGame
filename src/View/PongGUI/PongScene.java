package View.PongGUI;

import Model.Pong.Ball.Ball;
import Model.Pong.GoalKeeper.GoalKeeper;
import Model.Pong.PongLogic;
import Model.Pong.PongPlayer;
import View.utils.BarScene;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class PongScene extends BarScene {
    public static final int PONG_WIDTH = 640;
    public static final int PONG_HEIGHT = 360;
    public static final int BAR_HEIGHT = 40;
    public static final int BORDER_SIZE = 5;
    private static double xMultiplier = 1;
    private static double yMultiplier = 1;

    private final PongLogic pongLogic;

    //mainPart
    private Rectangle goalKeeper1 = new Rectangle();
    private Rectangle goalKeeper2 = new Rectangle();
    private Circle ball = new Circle();

    //bar
    private Text chronometer = new Text("0");
    private Text score1 = new Text("0");
    private Text score2 = new Text("0");


    public PongScene(PongLogic pongLogic) {
        super(new Group(), PONG_WIDTH + 2 * BORDER_SIZE, PONG_HEIGHT + BAR_HEIGHT + 2 *BORDER_SIZE, Color.BLACK);
        this.pongLogic = pongLogic;
        Group root = getMainPart();
        setBar();
        root.getChildren().add(new Rectangle(PONG_WIDTH + 2*BORDER_SIZE, PONG_HEIGHT + 2*BORDER_SIZE));

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //Drawing GoalKeepers
                drawGoalKeeper(pongLogic.getGoalKeeper1(), goalKeeper1);
                drawGoalKeeper(pongLogic.getGoalKeeper2(), goalKeeper2);

                //Drawing Ball
                drawBall(pongLogic.getBall());

                drawInformation();
            }
        };
        animationTimer.start();
        root.getChildren().addAll(ball, goalKeeper1, goalKeeper2);
    }

    public void setBar() {
        getBar().setMaxHeight(40);
        getBar().setMaxWidth(PONG_WIDTH + 2*BORDER_SIZE);
        getBar().setStyle("-fx-border-color: white;");
        getBar().setAlignment(Pos.CENTER);
        getBar().setPadding(new Insets(0,0,BORDER_SIZE,0));
        score1.setFill(Color.color((double)0x19/256,(double)0xfb/256,(double)0xff/256));
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
                rectangle.setFill(Color.color((double)0x19/256,(double)0xfb/256,(double)0xff/256));
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

    private void drawInformation(){
        score1.setText("" + ((PongPlayer)pongLogic.getPlayers().get(0)).getScore());
        score2.setText("" + ((PongPlayer)pongLogic.getPlayers().get(1)).getScore());
        // TODO: 5/28/2018  
    }

    private double castLogicalXToGraphical(double x) {
        return BORDER_SIZE + x*xMultiplier;
    }

    private double castLogicalYToGraphical(double y) {
        return BORDER_SIZE + y*yMultiplier;
    }
}
