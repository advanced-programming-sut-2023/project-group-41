package stronghold.networktest;

import stronghold.model.components.general.User;
import stronghold.model.utils.network.seth.Host;

import java.io.IOException;

public class HostTest {
    public static void main(String[] args) throws IOException {
        Host host = new Host();
        host.setHandleReceivedObjects(object -> {
            if(object instanceof User){
                System.out.println(((User) object).getUsername());
            }
            else{
                System.out.println(object.toString());
            }
        });
    }
}
