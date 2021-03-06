package Model.Pong;

import java.io.Serializable;

public class BoardProperties implements Serializable {
    public static final BoardProperties DEFAULT_BOARD;
    static  {
        DEFAULT_BOARD = new BoardProperties();
        DEFAULT_BOARD.width = PongLogic.DEFAULT_GAME_WIDTH;
        DEFAULT_BOARD.height = PongLogic.DEFAULT_GAME_HEIGHT;
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
