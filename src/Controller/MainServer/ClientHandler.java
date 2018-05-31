package Controller.MainServer;

import Controller.Packets.ServerPacket;
import Controller.utils.ServerPackageListener;
import Model.World;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable, ServerPackageListener {
    private World world;
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private boolean isSetup = false;

    public void handleSocket(World world, Socket socket) throws IOException {
        this.world = world;
        this.socket = socket;
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                ServerPacket serverPacket = (ServerPacket) objectInputStream.readObject();
                receive(serverPacket);
            } catch (ClassNotFoundException | ClassCastException e) {
                System.err.println("Invalid Packet from " + socket);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                // FIXME: 5/31/2018
                e.printStackTrace();
            }
        }
    }

    @Override
    public void receive(ServerPacket serverPacket) {
        if (!isSetup) {
            switch (serverPacket.getPacketType()) {
                case SIGN_UP:
                    // TODO: 5/31/2018
                    break;
                case LOG_IN:
                    // TODO: 5/31/2018
                    break;
                case GET_RANKS:
                    // TODO: 5/31/2018
                    break;
                default:
                    break;
            }
        } else {
            switch (serverPacket.getPacketType()) {

            }
        }
    }
}
