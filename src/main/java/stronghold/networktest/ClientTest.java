package stronghold.networktest;

import javafx.scene.layout.Pane;
import stronghold.model.utils.network.seth.Client;
import stronghold.model.utils.network.seth.Host;
import stronghold.model.utils.network.seth.NetworkNode;

import java.io.IOException;
import java.util.ArrayList;

public class ClientTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        String currentIp = "192.168.1.150";
        new Thread(() -> {
            try {
                Host host = new Host();

            } catch (
                    IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
        Thread.sleep(2000);
        new Thread(() -> {
            Client client = null;
            try {
                client = new Client(currentIp);
            } catch (
                    IOException e) {
                throw new RuntimeException(e);
            }
            while(true){
                client.sendMessageToHost("JAFAR SEDEII KHAR!");
                try {
                    Thread.sleep(200);
                } catch (
                        InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        Thread.sleep(2000);
        new Thread(() -> {
            Client client = null;
            try {
                client = new Client(currentIp);
            } catch (
                    IOException e) {
                throw new RuntimeException(e);
            }
            while(true){
                client.sendMessageToHost("NIMA KORDE BI GHEIRAT!");
                try {
                    Thread.sleep(250);
                } catch (
                        InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }).start();
    }
}
