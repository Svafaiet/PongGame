package Controller.Packets;

import java.io.Serializable;

public enum ClientPacketType implements Serializable {
    ERROR_MASSAGE,
    SUCCESSFUL_LOGIN,
    SUCCESSFUL_LOGOUT,
    PROFILES,
    WAITING_GAMES,
    GAME_STARTED,
    GAME_PROPERTIES,
    GAME_FINISHED
}
