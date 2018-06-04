package Controller.Packets;

import java.io.Serializable;

public enum ClientPacketType implements Serializable {
    ERROR_MASSAGE(true),
    SUCCESSFUL_LOGIN(true),
    SUCCESSFUL_LOGOUT(true),
    PROFILES(true),
    WAITING_GAMES(true),
    GAME_STARTED(true),
    GAME_PROPERTIES(false),
    GAME_FINISHED(false);

    boolean isHandShaker;

    ClientPacketType(boolean isHandShaker) {
        this.isHandShaker = isHandShaker;
    }

    public boolean isHandShaker() {
        return isHandShaker;
    }
}
