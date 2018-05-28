package Model.Pong;

import java.io.Serializable;

public class BoardProperties implements Serializable {
    public static final BoardProperties DEFAULT_BOARD;
    static  {
        DEFAULT_BOARD = new BoardProperties();
        DEFAULT_BOARD.width = 400;
        DEFAULT_BOARD.height = 200;
    }

    private int width;
    private int height;

    public BoardProperties() {
    }

    public BoardProperties(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
