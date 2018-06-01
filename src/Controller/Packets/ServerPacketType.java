package Controller.Packets;

import java.io.Serializable;

public enum ServerPacketType implements Serializable {
    LOG_IN, LOG_OUT, SIGN_UP, MAKE_GAME, JOIN, GAME_ACTION, GET_AVAILABLE_GAMES, GET_RANKS
}
