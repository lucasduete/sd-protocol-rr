package io.github.lucasduete.sd.protocolrr;

import io.github.lucasduete.sd.protocolrr.interfaces.ServerInterface;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public final class Server implements ServerInterface {

    private final int MSG_SIZE = 40;

    private int clientPort;
    private DatagramSocket socket;
    private InetAddress clientHost;

    public Server() {

    }

    public void start() {

        try {
            while (true) {
                this.socket = new DatagramSocket(22222);

                byte[] msg = getRequest();

                byte[] reply = calculate(msg);

                sendReply(reply, this.clientHost, this.clientPort);
            }

        } catch (SocketException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public byte[] getRequest() {
        DatagramPacket RXpacket = null;

        byte[] msg = new byte[MSG_SIZE];

        try {
            RXpacket = new DatagramPacket(msg, MSG_SIZE);
            System.out.println("Server Waiting");
            socket.receive(RXpacket);
            System.out.println("Server Reveice: " + printMsg(msg));

            this.clientHost = InetAddress.getByName(new String(ByteBuffer.wrap(msg, 8, 16).array()).trim());
            this.clientPort = ByteBuffer.wrap(msg, 24, 4).getInt();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return msg;
    }

    @Override
    public void sendReply(byte[] reply, InetAddress clienteHost, int clientPort) {
        DatagramSocket cliente = null;

        try {
            cliente = new DatagramSocket();

            byte[] msg = reply;
            System.arraycopy(convertToByte(1), 0, msg, 0, 4);

            DatagramPacket packet = new DatagramPacket(msg, MSG_SIZE, clienteHost, clientPort);
            cliente.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        cliente.close();
        this.socket.close();
    }

    private byte[] convertToByte(Integer value) {
        return ByteBuffer.allocate(Integer.SIZE / 8).putInt(value).array();
    }

    private String printMsg(byte[] msg) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("MessageType -> " + ByteBuffer.wrap(msg, 0, 4).getInt());
        stringBuilder.append("; RequestId -> " + ByteBuffer.wrap(msg, 4, 4).getInt());
        stringBuilder.append("; RemoteReference : Server -> " + new String(ByteBuffer.wrap(msg, 8, 16).array()).trim());
        stringBuilder.append("; RemoteReference : Port -> " + ByteBuffer.wrap(msg, 24, 4).getInt());
        stringBuilder.append("; OperationId -> " + ByteBuffer.wrap(msg, 28, 4).getInt());
        stringBuilder.append("; Number 1 / Result -> " + ByteBuffer.wrap(msg, 32, 4).getInt());
        stringBuilder.append("; Number 2 -> " + ByteBuffer.wrap(msg, 36, 4).getInt());

        return stringBuilder.toString();
    }

    private byte[] calculate(byte[] msg) {
        int operationId = ByteBuffer.wrap(msg, 28, 4).getInt();
        int num1 = ByteBuffer.wrap(msg, 32, 4).getInt();
        int num2 = ByteBuffer.wrap(msg, 36, 4).getInt();

        switch (operationId) {
            case 0:
                System.arraycopy(Arrays.copyOfRange(convertToByte(num1 + num2), 0, 4), 0, msg, 32, 4);
                break;
            case 1:
                System.arraycopy(Arrays.copyOfRange(convertToByte(num1 - num2), 0, 4), 0, msg, 32, 4);
                break;
        }

        return msg;
    }


}
