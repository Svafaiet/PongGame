package Model.Pong;

public abstract class Location {

    public static boolean isInRange(double x, double l, double h) {
        return (x >= l && x < h);
    }
}
