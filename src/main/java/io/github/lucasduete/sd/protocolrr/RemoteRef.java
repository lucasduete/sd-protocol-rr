package io.github.lucasduete.sd.protocolrr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RemoteRef implements Serializable {

    private List<String> serversAddr;
    private List<Integer> serversPort;

    {
        this.serversAddr = new ArrayList<>();
        this.serversPort = new ArrayList<>();
    }

    public RemoteRef() {

    }

    public List<String> getServersAddr() {
        return serversAddr;
    }

    public List<Integer> getServersPort() {
        return serversPort;
    }

    public void addServerAddr(String serverAddr, Integer serverPort) {
        this.serversAddr.add(serverAddr);
        this.serversPort.add(serverPort);
    }

}
