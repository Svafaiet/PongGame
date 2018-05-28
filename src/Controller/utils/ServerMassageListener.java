package Controller.utils;

import Controller.Packets.ServerPacket;

public interface ServerMassageListener {
    void receive(ServerPacket serverPacket);
}
