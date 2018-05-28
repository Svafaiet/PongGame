package Controller.utils;

import Controller.Packets.ServerPacket;

public interface ServerPackageListener {
    void receive(ServerPacket serverPacket);
}
