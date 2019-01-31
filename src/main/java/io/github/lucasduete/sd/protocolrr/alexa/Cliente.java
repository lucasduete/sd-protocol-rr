package io.github.lucasduete.sd.protocolrr.alexa;

import io.github.lucasduete.sd.protocolrr.interfaces.ClientInterface;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class Cliente implements ClientInterface {

    @Override
    public byte[] doOperation(RemoteRef rr, int operationId, byte[] arguments) {
        DatagramSocket cliente = null;

        try {
            cliente = new DatagramSocket();

            final int msgsize = arguments.length + 2;
            byte[] temp = new byte[]{Byte.decode(String.valueOf(msgsize)), Byte.decode(String.valueOf(operationId))};
            byte[] bytes = Arrays.copyOf(temp, msgsize);

            InetAddress inetAddress = InetAddress.getByName(rr.getServerList().get(0).toString());
            System.arraycopy(arguments, 0, bytes, temp.length, arguments.length);
            //
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, inetAddress, 1122);
            //
            cliente.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        cliente.close();
        return null;
    }
}
