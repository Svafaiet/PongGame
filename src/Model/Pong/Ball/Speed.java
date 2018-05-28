package Model.Pong.Ball;

import java.io.Serializable;

public class Speed implements Serializable {
    private double size;
    private double horizontalDir;
    private double verticalDir;

    public Speed(double horizontalDir, double verticalDir) {
        this.horizontalDir = horizontalDir;
        this.verticalDir = verticalDir;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getHorizontalDir() {
        return horizontalDir;
    }

    public double getVerticalDir() {
        return verticalDir;
    }

    public double getHorizontalSpeed() {
        return horizontalDir * size;
    }

    public void add(Speed speed) {
        double horizontalSpeed = getHorizontalSpeed() + speed.getHorizontalSpeed();
        double verticalSpeed = getVerticalSpeed() + speed.getVerticalSpeed();
        size = Math.sqrt(Math.pow(horizontalSpeed, 2) + Math.pow(verticalSpeed, 2));
        horizontalDir = horizontalSpeed/size;
        verticalDir = verticalSpeed/size;
    }

    public double getVerticalSpeed() {
        return verticalDir * size;
    }

    public void reverseHorizontalSpeed() {
        horizontalDir = -horizontalDir;
    }

    public void reverseVerticalSpeed(){
        verticalDir = -verticalDir;
    }

    public void changeDirection(double horizontalDir, double verticalDir) {
        this.horizontalDir = horizontalDir;
        this.verticalDir = verticalDir;
    }
}
