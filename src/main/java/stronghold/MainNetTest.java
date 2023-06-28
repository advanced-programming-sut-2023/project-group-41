package stronghold;

import stronghold.model.utils.network.seth.Host;
import stronghold.model.utils.network.seth.Client;

import java.io.IOException;
import java.util.Scanner;

public class MainNetTest {
    public static void main(String[] args) throws IOException {
        Host host = new Host();
        Scanner scanner = new Scanner(System.in);
        while (true){
            if(scanner.nextLine().equals("1")){
                Client networkNode = new Client("localhost");
                networkNode.sendMessageToHost("22");
            }
        }

    }
}
