package stronghold.networktest;

import javafx.scene.control.Slider;
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
                client = new Client();
            } catch (
                    IOException e) {
                throw new RuntimeException(e);
            }
            try {
                UsersDB.usersDB.fromJSON();
            } catch (
                    IOException e) {
                throw new RuntimeException(e);
            }
            while(true){
                client.sendObjectToServer(UsersDB.usersDB);
            }
        }).start();

    }
}
