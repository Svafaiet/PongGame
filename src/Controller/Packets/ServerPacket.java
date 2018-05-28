package Controller.Packets;


/*
 * serverPacket :
 * ADD_PROFILE profileName
 * START gameType gameName
 * JOIN gameName
 * PONG_ACTION [UP|DOWN]
 * GET_AVAILABLE_GAMES
 * GET_PROFILES
 */

public class ServerPacket {
    private String packetType;
    private String firstArgument;
    private String secondArgument;
    private String fromMassage;

    public ServerPacket() {
    }

    public ServerPacket(String fromMassage) {
        this.fromMassage = fromMassage;
    }

    public String getSecondArgument() {
        return secondArgument;
    }

    public void setSecondArgument(String secondArgument) {
        this.secondArgument = secondArgument;
    }

    public String getFromMassage() {
        return fromMassage;
    }

    public void setFromMassage(String fromMassage) {
        this.fromMassage = fromMassage;
    }

    public String getPacketType() {
        return packetType;
    }

    public void setPacketType(String packetType) {
        this.packetType = packetType;
    }

    public String getFirstArgument() {
        return firstArgument;
    }

    public void setFirstArgument(String firstArgument) {
        this.firstArgument = firstArgument;
    }
}
