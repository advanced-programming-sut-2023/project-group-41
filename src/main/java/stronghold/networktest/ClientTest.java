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

            User user = new User("lolipop", "bruv", "loli", "e@c.c", 1, "1", "111");
            RequestObject requestObject1 = new RequestObject("authenticate", "anakin", "gioiiiigg");
            RequestObject requestObject2 = new RequestObject("register", user);

            client.sendObjectToServer(requestObject1);
            String str;

            str = client.recieveMessgeFromHost();
            System.out.println(str);
            client.sendObjectToServer(requestObject2);
            str = client.recieveMessgeFromHost();
            System.out.println(str);
            
        }).start();

    }
}
