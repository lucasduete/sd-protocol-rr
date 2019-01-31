package io.github.lucasduete.sd.protocolrr.interfaces;

import java.net.InetAddress;

public interface ServerInterface {
    byte[] getRequest();
    void  sendReply(byte[] reply, InetAddress clienteHost, int clientPort);
}
