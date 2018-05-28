package Model.Pong.PowerUps;

import Model.Pong.PongLogic;

import java.time.Duration;

public abstract class PongPowerUp {
    private Duration duration;
    public PongPowerUp(Duration duration) {
        this.duration = duration;
    }
    public abstract void enableEffect();
    public abstract void disableEffect();
}
