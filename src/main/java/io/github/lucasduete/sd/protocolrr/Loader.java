package io.github.lucasduete.sd.protocolrr;

import java.nio.ByteBuffer;

public class Loader {

    public static void main(String[] args) {

        new Thread(() -> {
            new Server().start();
        }).start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        Client client = new Client();

        RemoteRef remoteRef = new RemoteRef();
        remoteRef.addServerAddr("127.0.0.1", 11111);

        byte[] arguments = new byte[8];

        System.arraycopy(ByteBuffer.allocate(Integer.SIZE / 8).putInt(20).array(), 0, arguments, 0, 4);
        System.arraycopy(ByteBuffer.allocate(Integer.SIZE / 8).putInt(40).array(), 0, arguments, 4, 4);

        client.doOperation(remoteRef, 0, arguments);
    }

}
