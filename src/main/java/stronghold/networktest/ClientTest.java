package stronghold.networktest;

import javafx.scene.layout.Pane;
import stronghold.model.components.general.User;
import stronghold.model.database.UsersDB;
import stronghold.model.utils.network.seth.Client;
import stronghold.model.utils.network.seth.Host;
import stronghold.model.utils.network.seth.NetworkNode;

import java.io.IOException;
import java.util.ArrayList;

public class ClientTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        new Thread(() -> {
            Client client = null;
            try {
                client = new Client("192.168.1.150");
            } catch (
                    IOException e) {
                throw new RuntimeException(e);
            }
            while(true){
                User user = new User("test","test","test","test",
                        1,"test", "test");
                client.sendObjectToServer(user);
                try {
                    Thread.sleep(200);
                } catch (
                        InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }
}
