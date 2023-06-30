package stronghold.networktest;

import javafx.application.Application;
import javafx.stage.Stage;
import stronghold.model.components.general.User;
import stronghold.model.utils.network.seth.Host;

import java.io.IOException;
import java.net.Socket;

public class HostTest extends Application {

    @Override
    public void start(Stage primaryStage){
        System.out.println("KHIAR");

    }
    public static void main(String[] args) throws IOException {
        Host host = new Host();

        host.setHandleReceivedObjects(request -> {
            Object object = request.getReceivedObject();
            Socket socket = request.getSender();

            if(object instanceof User){
                try {
                    host.sendObjectToClient(socket, ((User) object).getUsername());
                } catch (
                        IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                System.out.println(object.toString());
            }
        });
    }
}
