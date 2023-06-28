package stronghold.networktest;

import stronghold.model.utils.network.seth.Client;
import stronghold.model.utils.network.seth.NetworkNode;

import java.io.IOException;

public class ClientTest {
    public static void main(String[] args) throws IOException {
        NetworkNode client = new NetworkNode();
        System.out.println(client.scanForActiveSubnets());
        
    }
}
