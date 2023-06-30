package stronghold.model.utils.network.authenticator;

import javafx.beans.binding.ObjectExpression;
import stronghold.controller.SignUpMenuController;
import stronghold.controller.graphical.LoginController;
import stronghold.model.components.general.User;
import stronghold.model.database.UsersDB;
import stronghold.model.utils.network.seth.Host;
import stronghold.model.utils.network.seth.Request;
import stronghold.model.utils.network.seth.RequestObject;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class AuthenticatorServer {

    static Host host;

    public static Host getHost() {
        return host;
    }

    public static void setHost(Host host) {
        AuthenticatorServer.host = host;
    }

    public static void runAuthenticator() throws IOException {
        UsersDB.usersDB.fromJSON();
        host.setHandleReceivedObjects(request -> {
            Object object = request.getReceivedObject();
            Socket sender = request.getSender();

            if(object instanceof RequestObject){
                RequestObject requestObject = (RequestObject) object;

                String requestString = requestObject.getSerialCode();
                ArrayList<Object> requestList = requestObject.getArgs();

                if(requestString.equals("authenticate")){
                    String username = (String) requestList.get(0);
                    String password = (String) requestList.get(1);

                    System.out.println(username + "\t" + password);
                    
                    
                    boolean authenticated = SignUpMenuController.authenticate(username, password);
                    if(authenticated){
                        try {
                            host.sendMessageToClient(sender, "OK");
                        } catch (
                                IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else{
                        try {
                            host.sendMessageToClient(sender, "NOTOK");
                        } catch (
                                IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
    }
}
