package stronghold.networktest;

import stronghold.model.components.general.User;
import stronghold.model.database.UsersDB;
import stronghold.model.utils.network.seth.Host;

import java.io.IOException;

public class HostTest {
    public static void main(String[] args) throws IOException {
        Host host = new Host();
        host.setHandleReceivedObjects(object -> {
            if(object instanceof UsersDB){
                UsersDB usersDB = (UsersDB) object;
                System.out.println(usersDB.getAtIndex(0).getUsername());
            }
            else{
                System.out.println(object.toString());
            }
        });
    }
}
