package Controller.utils;

import Controller.Packets.ClientPacket;

public interface ClientPackageListener {
    void receive(ClientPacket clientPacket);
}
