package stronghold.networktest;

import stronghold.model.utils.network.server.Server;
import stronghold.model.utils.network.seth.Host;

import java.io.IOException;

public class HostTest{


    public static void main(String[] args) throws IOException {
        Server.setHost(new Host());
        Server.runAuthenticator();
    }
}
