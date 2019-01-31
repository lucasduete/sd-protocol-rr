package io.github.lucasduete.sd.protocolrr.alexa;

import java.util.List;

public class RemoteRef {

    private List<Integer> serverList;

    public RemoteRef() {
    }

    public RemoteRef(List<Integer> serverList) {
        this.serverList = serverList;
    }

    public List<Integer> getServerList() {
        return serverList;
    }

    public void setServerList(List<Integer> serverList) {
        this.serverList = serverList;
    }
}
