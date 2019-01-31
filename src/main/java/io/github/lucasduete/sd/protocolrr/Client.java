package io.github.lucasduete.sd.protocolrr;

import io.github.lucasduete.sd.protocolrr.RemoteRef;
import io.github.lucasduete.sd.protocolrr.interfaces.ClientInterface;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public final class Client implements ClientInterface {

    private static final AtomicInteger requestId = new AtomicInteger(1);
    private final Integer MSG_SIZE = 40;

    @Override
    public byte[] doOperation(RemoteRef rr, int operationId, byte[] arguments) {
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket();

            InetAddress inetAddress = InetAddress.getByName("localhost");

            byte[] msg = generateMessage(rr, operationId, arguments);

            DatagramPacket packet = new DatagramPacket(msg, MSG_SIZE, inetAddress, 22222);
            socket.send(packet);
            System.out.println("Client Send: " + printMsg(msg));

            socket = new DatagramSocket(rr.getServersPort().get(0));
            DatagramPacket RXpacket = null;

            msg = new byte[MSG_SIZE];

            RXpacket = new DatagramPacket(msg, MSG_SIZE);
            System.out.println("Client Waiting");
            socket.receive(RXpacket);
            System.out.println("Client Reveice: " + printMsg(msg));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private byte[] generateMessage(RemoteRef rr, Integer operationFlag, byte[] arguments) {
        byte[] message = new byte[MSG_SIZE];

        System.arraycopy(convertToByte(0), 0, message, 0, 4);
        System.arraycopy(convertToByte(requestId.getAndIncrement()), 0, message, 4, 4);
        System.arraycopy(convertToByte(rr.getServersAddr().get(0)), 0, message, 8, 16);
        System.arraycopy(convertToByte(rr.getServersPort().get(0)), 0, message, 24, 4);
        System.arraycopy(convertToByte(operationFlag), 0, message, 28, 4);
        System.arraycopy(Arrays.copyOfRange(arguments, 0, 4), 0, message, 32, 4);
        System.arraycopy(Arrays.copyOfRange(arguments, 4, 8), 0, message, 36, 4);

        return message;
    }

    private byte[] convertToByte(Integer value) {
        return ByteBuffer.allocate(Integer.SIZE / 8).putInt(value).array();
    }

    private byte[] convertToByte(String value) {
        return ByteBuffer.allocate(16).put(value.getBytes()).array();
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

}
