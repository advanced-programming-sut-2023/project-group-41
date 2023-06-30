package stronghold.networktest;

import javafx.application.Application;
import javafx.stage.Stage;
import stronghold.model.utils.network.seth.Host;
import stronghold.view.graphics.RegisterView;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class HostTest extends Application {

    @Override
    public void start(Stage primaryStage){
        System.out.println("KHIAR");

    }
    public static void main(String[] args) throws IOException {
        Host host = new Host();
        AtomicBoolean hasBeenLaunched = new AtomicBoolean(false);
        host.setHandleReceivedObjects(object -> {
            if(object instanceof RegisterView){
                RegisterView registerView = (RegisterView) object;
                try {
                    if(!hasBeenLaunched.get()){
                        registerView.start(new Stage());
                        hasBeenLaunched.set(true);
                    }
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
