package stronghold.networktest;

import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import stronghold.model.components.general.User;
import stronghold.model.database.UsersDB;
import stronghold.model.utils.network.seth.Client;
import stronghold.model.utils.network.seth.Host;
import stronghold.model.utils.network.seth.NetworkNode;
import stronghold.model.utils.network.seth.RequestObject;
import stronghold.view.graphics.RegisterView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            ArrayList<Object> creds1 = new ArrayList<>();
            creds1.addAll(List.of(new String[]{"anakin", "giiggg"}));
            ArrayList<Object> creds2 = new ArrayList<>();
            creds2.addAll(List.of(new String[]{"skywalker", "An@k1n"}));

            RequestObject requestObject1 = new RequestObject("authenticate", creds1);
            RequestObject requestObject2 = new RequestObject("authenticate", creds2);

            client.sendObjectToServer(requestObject1);
            try {
                Thread.sleep(1000);
            } catch (
                    InterruptedException e) {
                throw new RuntimeException(e);
            }
            client.sendObjectToServer(requestObject2);
        }).start();

    }
}
