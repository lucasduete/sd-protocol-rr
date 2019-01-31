package io.github.lucasduete.sd.protocolrr.alexa;

import io.github.lucasduete.sd.protocolrr.interfaces.ServerInterface;

import java.io.IOException;
import java.net.*;

public class Server implements ServerInterface {

    @Override
    public byte[] getRequest() {
        DatagramSocket socket = null;
        DatagramPacket RXpacket = null;
        byte [] temp = null;

        {
            try {
                socket = new DatagramSocket(1122);
                temp = new byte[256];
                RXpacket = new DatagramPacket(temp, temp.length);
                socket.receive(RXpacket);
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            InetAddress inetAddress = RXpacket.getAddress();
            int port = RXpacket.getPort();
            socket.getLocalAddress();
            //
            sendReply(temp, inetAddress, port);
        }
        return new byte[0];
    }

    @Override
    public void sendReply(byte[] reply, InetAddress clienteHost, int clientPort) {
        DatagramSocket cliente = null;
        //
        try {
            cliente = new DatagramSocket();
            //
            DatagramPacket packet = new DatagramPacket(reply, reply.length, clienteHost, clientPort);
            cliente.send(packet);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
