package Controller.Packets;

public class ClientPacket {
    private ClientPacketType packetType;
    private Object[] arguments = new Object[0];
    private String serverName;

    public ClientPacket() {
    }

    public ClientPacket(String serverName) {
        this.serverName = serverName;
    }

    public String getServerName() {
        return serverName;
    }

    public ClientPacketType getPacketType() {
        return packetType;
    }

    public void addElements(Object... newArguments) {
        Object[] newArgs = new Object[arguments.length + newArguments.length];
        System.arraycopy(arguments, 0, newArgs, 0, arguments.length);
        System.arraycopy(newArguments, 0, newArgs, arguments.length, newArguments.length);
        arguments = newArgs;
    }

    public void setPacketType(ClientPacketType packetType) {
        this.packetType = packetType;
    }

    public Object getArgument(int i) {
        return arguments[i];
    }
}
