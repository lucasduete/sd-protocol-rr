package io.github.lucasduete.sd.protocolrr.interfaces;

import java.net.InetAddress;

public interface ServerInterface {

    public byte[] getRequest();
    public void sendReply(byte[] reply, InetAddress clienteHost, int clientPort);
}
