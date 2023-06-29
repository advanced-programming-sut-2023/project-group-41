package stronghold;

import stronghold.model.utils.network.seth.Host;
import stronghold.model.utils.network.seth.Client;
import stronghold.networktest.ClientTest;
import stronghold.networktest.HostTest;

import java.io.IOException;
import java.util.Scanner;

public class MainNetTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        HostTest.main(args);
        ClientTest.main(args);
    }
}
