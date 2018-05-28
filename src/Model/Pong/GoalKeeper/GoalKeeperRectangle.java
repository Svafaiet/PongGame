package Model.Pong.GoalKeeper;

import java.io.Serializable;

public class GoalKeeperRectangle implements Serializable {
    private double height;
    private double width;
    private double centerX;
    private double centerY;

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCornerX() {
        return centerX - width/2;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getCornerY() {
        return centerY - height/2;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }
}
