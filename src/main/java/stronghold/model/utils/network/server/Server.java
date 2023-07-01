package stronghold.model.utils.network.server;

import stronghold.controller.SignUpMenuController;
import stronghold.model.components.general.User;
import stronghold.model.database.UsersDB;
import stronghold.model.utils.network.seth.Host;
import stronghold.model.utils.network.seth.RequestObject;

import java.io.IOException;
import java.net.Socket;

public class Server {

    static Host host;

    public static Host getHost() {
        return host;
    }

    public static void setHost(Host host) {
        Server.host = host;
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
                            host.sendMessageToClient(sender, "true");
                        } catch (
                                IOException e) {

                            throw new RuntimeException(e);
                        }
                    }
                    else{
                        try {

                            host.sendMessageToClient(sender, "false");
                        } catch (
                                IOException e) {


                            throw new RuntimeException(e);
                        }
                    }
                }
                else if(requestString.equals("register")){
                    User user = (User) requestList[0];
                    if(UsersDB.usersDB.getUserByUsername(user.getUsername()) != null){
                        try {
                            host.sendMessageToClient(sender, "false");
                        } catch (
                                IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    else {
                        UsersDB.usersDB.addUser(user);
                        try {
                            UsersDB.usersDB.toJSON();
                        } catch (
                                IOException e) {
                            try {
                                host.sendMessageToClient(sender, "false");
                            } catch (
                                    IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                        try {
                            host.sendMessageToClient(sender, "true");
                        } catch (
                                IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                else if(requestString.equals("usernameexists")){
                    String username = (String) requestList[0];
                    if(UsersDB.usersDB.getUserByUsername(username) == null){
                        try {
                            host.sendMessageToClient(sender,"false");
                        } catch (
                                IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else{
                        try {
                            host.sendMessageToClient(sender, "true");
                        } catch (
                                IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                else if(requestString.equals("emailexists")){
                    String email = (String) requestList[0];
                    if(UsersDB.usersDB.getUserByEmail(email) == null){
                        try {
                            host.sendMessageToClient(sender,"false");
                        } catch (
                                IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else{
                        try {
                            host.sendMessageToClient(sender,"true");
                        } catch (
                                IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                else if(requestString.equals("edituser")){
                    User newUser = (User) requestList[0];
                    UsersDB.usersDB.update(newUser);
                    try {
                        UsersDB.usersDB.toJSON();
                    } catch (
                            IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        host.sendMessageToClient(sender, "true");
                    } catch (
                            IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }
}
