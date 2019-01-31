package io.github.lucasduete.sd.protocolrr.interfaces;

import io.github.lucasduete.sd.protocolrr.RemoteRef;

public interface ClientInterface {

    public byte[] doOperation(RemoteRef rr, int operationId, byte[] arguments);
}
