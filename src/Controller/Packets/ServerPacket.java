package Controller.Packets;


/*
 * serverPacket :
 * SIGN_UP profileName
 * START gameType gameName
 * JOIN gameName
 * GAME_ACTION [UP|DOWN]
 * GET_AVAILABLE_GAMES
 * GET_PROFILES
 */

import java.io.Serializable;
import java.util.Arrays;

public class ServerPacket implements Serializable {
    private ServerPacketType packetType;
    private Object[] arguments = new Object[0];
    private String fromMassage;

    public ServerPacket() {
    }

    public ServerPacket(String fromMassage) {
        this.fromMassage = fromMassage;
    }

    public String getFromMassage() {
        return fromMassage;
    }

    public ServerPacketType getPacketType() {
        return packetType;
    }

    public void addElements(Object... newArguments) {
        Object[] newArgs = new Object[arguments.length + newArguments.length];
        System.arraycopy(arguments, 0, newArgs, 0, arguments.length);
        System.arraycopy(newArguments, 0, newArgs, arguments.length, newArguments.length);
        arguments = newArgs;
    }

    public void setPacketType(ServerPacketType packetType) {
        this.packetType = packetType;
    }

    public Object getArgument(int i) {
        return arguments[i];
    }

    public void setFromMassage(String fromMassage) {
        this.fromMassage = fromMassage;
    }

    @Override
    public String toString() {
        return "ServerPacket{" +
                "packetType=" + packetType +
                ", arguments=" + Arrays.toString(arguments) +
                ", fromMassage='" + fromMassage + '\'' +
                '}';
    }
}
