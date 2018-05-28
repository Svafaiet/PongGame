package Model.Pong;

public abstract class Location {
    public static boolean isInRange(double x, int l, int h) {
        return (x >= l && x < h);
    }

    public static boolean isInRange(double x, double l, double h) {
        return (x >= l && x < h);
    }
}
