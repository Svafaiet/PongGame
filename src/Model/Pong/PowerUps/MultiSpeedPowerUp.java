package Model.Pong.PowerUps;

import java.time.Duration;

public class MultiSpeedPowerUp extends PongPowerUp {

    public static final int MULTI_SPEED_POWER_UP_DURATION_SEC = 7;

    public MultiSpeedPowerUp() {
        super(Duration.ofSeconds(MULTI_SPEED_POWER_UP_DURATION_SEC));
    }

    @Override
    public void enableEffect() {

    }

    @Override
    public void disableEffect() {

    }
}