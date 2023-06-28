package stronghold.networktest;

import stronghold.model.utils.network.seth.Client;
import stronghold.model.utils.network.seth.Host;
import stronghold.model.utils.network.seth.NetworkNode;

import java.io.IOException;
import java.util.ArrayList;

public class ClientTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client(new NetworkNode().findFirstSubnetAvailable());
        while(true){
            client.sendMessageToHost("KOS NANE JAFAR!");
            Thread.sleep(200);
        }
    }
}
