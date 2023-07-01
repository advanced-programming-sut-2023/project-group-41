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
                Object[] requestList = requestObject.getArgs();

                if(requestString.equals("authenticate")){
                    String username = (String) requestList[0];
                    String password = (String) requestList[1];

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
                else if(requestString.equals("register")){
                    User user = (User) requestList[0];
                    UsersDB.usersDB.addUser(user);
                    try {
                        UsersDB.usersDB.toJSON();
                    } catch (
                            IOException e) {
                        try {
                            host.sendMessageToClient(sender, "NOTOK");
                        } catch (
                                IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    try {
                        host.sendMessageToClient(sender, "OK");
                    } catch (
                            IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}
