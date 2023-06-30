package stronghold.networktest;

import javafx.application.Application;
import javafx.stage.Stage;
import stronghold.model.components.general.User;
import stronghold.model.database.UsersDB;
import stronghold.model.utils.network.seth.Host;
import stronghold.view.graphics.RegisterView;

import java.io.IOException;

public class HostTest extends Application {

    @Override
    public void start(Stage primaryStage){
        System.out.println("KHIAR");

    }
    public static void main(String[] args) throws IOException {
        Host host = new Host();
        host.setHandleReceivedObjects(object -> {
            if(object instanceof RegisterView){
                RegisterView registerView = (RegisterView) object;
                try {
                    registerView.start(new Stage());
                } catch (
                        Exception e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                System.out.println(object.toString());
            }
        });
    }
}
