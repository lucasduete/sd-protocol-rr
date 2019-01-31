package io.github.lucasduete.sd.protocolrr.alexa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Loader {

    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        Server servidor = new Server();
        RemoteRef rr = new RemoteRef();
        Random random = new Random();
        List<Integer> integerList = new ArrayList<>();
        //
        integerList.add(1122);
        rr.setServerList(integerList);
        int aux = random.nextInt(101);
        byte[] temp = new byte[]{Byte.decode(String.valueOf(0)),Byte.decode(String.valueOf(1)), Byte.decode(String.valueOf(2))};
        //
        cliente.doOperation(rr, aux, temp);
    }
}
