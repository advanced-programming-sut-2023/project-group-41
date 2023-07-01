package stronghold.model.utils.network.server;

import stronghold.controller.SignUpMenuController;
import stronghold.model.components.chatrooms.Message;
import stronghold.model.components.chatrooms.Reaction;
import stronghold.model.components.chatrooms.Room;
import stronghold.model.components.general.User;
import stronghold.model.components.lobby.Game;
import stronghold.model.database.*;
import stronghold.model.utils.network.seth.Host;
import stronghold.model.utils.network.seth.RequestObject;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

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
                    String token = (String) requestList[0];
                    boolean authenticated = UsersDB.usersDB.tokenBasedAuth(token);
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
                    
                    User oldUser = (User) requestList[0];
                    User newUser = (User) requestList[1];

                    UsersDB.usersDB.updateByOldUser(oldUser, newUser);
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
                        throw new RuntimeException(e);
                    }
                    try {
                        UsersDB.usersDB.fromJSON();
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
                else if (requestString.equals("getUserRoom")) {
                    User usr = UsersDB.usersDB.getUserByUsername((String) requestList[0]);
                    try {
                        host.sendObjectToClient(sender, RoomsDB.getInstance().getUserRooms(usr));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                else if (requestString.equals("editMessage")) {
                    Message message = (Message) requestList[0];
                    message = RoomsDB.getInstance().getRoomByObj(message.getRoom()).getMessageByObj(message);
                    String text = (String) requestList[1];
                    message.setText(text);
                    try {
                        host.sendMessageToClient(sender, "True");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (requestString.equals("delMessage")) {
                    Message message = (Message) requestList[0];
                    message = RoomsDB.getInstance().getRoomByObj(message.getRoom()).getMessageByObj(message);
                    message.del();
                    try {
                        host.sendMessageToClient(sender, "True");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (requestString.equals("incReaction")) {
                    Message message = (Message) requestList[0];
                    message = RoomsDB.getInstance().getRoomByObj(message.getRoom()).getMessageByObj(message);
                    Reaction reaction = (Reaction) requestList[1];

                    message.incReactionByOne(reaction);
                    try {
                        host.sendMessageToClient(sender, "True");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                else if (requestString.equals("createRoom")) {
                    User user =  UsersDB.usersDB.getUserByUsername((String) requestList[0]);
                    String roomName = (String) requestList[1];
                    Room room = new Room(user, roomName);
                    try {
                        host.sendObjectToClient(sender, room);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                else if (requestString.equals("createMessage")) {
                    Message message = new Message(RoomsDB.getInstance().getRoomByObj(requestList[0]), UsersDB.usersDB.getUserByUsername((String) requestList[1]),
                            (String) requestList[2], (String) requestList[3]);
                    message.setSendMessage(true);

                    try {
                        host.sendObjectToClient(sender, message);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (requestString.equals("addNewUser")) {
                    Room room = RoomsDB.getInstance().getRoomByObj(requestList[0]);

                    try {
                        room.addUser( UsersDB.usersDB.getUserByUsername((String) requestList[1]));
                        host.sendMessageToClient(sender, "True");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (requestString.equals("getRoomMessages")) {
                    Room room = RoomsDB.getInstance().getRoomByObj((Room) requestList[0]);
                    try {
                        host.sendObjectToClient(sender, room.getMessages());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if(requestString.equals("createANewLobbyGame")){
                    Game game=new Game((String) requestList[0],(Integer)requestList[1],(boolean) requestList[2],(User) requestList[3]);
                    try {
                        host.sendObjectToClient(sender,game);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        GamesDB.getInstance().update();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if(requestString.equals("getGamesList")){
                    int now=new  Date().getMinutes();
                    ArrayList<Game> aliveGames=new ArrayList<>();
                    for (Game game : GamesDB.getInstance().getGames()) {
                        if(now-game.getLastMin()<5){
                            aliveGames.add(game);
                        }
                    }
                    GamesDB.getInstance().getGames().clear();
                    for (Game aliveGame : aliveGames) {
                        GamesDB.getInstance().getGames().add(aliveGame);
                    }
                    try {
                        host.sendObjectToClient(sender,GamesDB.getInstance().getGames());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        GamesDB.getInstance().update();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if(requestString.equals("removeFromPlayersList")){
                    if(GamesDB.getInstance().getGameByUsername((String) requestList[1])!=null){
                        if(GamesDB.getInstance().getGameByUsername((String) requestList[1]).getUsers().contains((User) requestList[0])){
                            GamesDB.getInstance().getGameByUsername((String) requestList[1]).getUsers().remove((User) requestList[0]);
                        }
                    }
                    if(GamesDB.getInstance().getGameByUsername((String) requestList[1]).getUsers().size()==0){
                        GamesDB.getInstance().getGames().remove(GamesDB.getInstance().getGameByUsername((String) requestList[1]));
                    }
                    else if(GamesDB.getInstance().getGameByUsername((String) requestList[1]).getHost().equals((User) requestList[0])){
                        GamesDB.getInstance().getGameByUsername((String) requestList[1]).setHost(GamesDB.getInstance().getGameByUsername((String) requestList[1]).getUsers().get(0));
                    }
                    try {
                        host.sendMessageToClient(sender,"removedPlayer");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        GamesDB.getInstance().update();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                else if(requestString.equals("joiningAGame")){
                    if(GamesDB.getInstance().getGameByUsername((String) requestList[0]).getCapacity()>GamesDB.getInstance().getGameByUsername((String) requestList[0]).getUsers().size()) {
                        GamesDB.getInstance().getGameByUsername((String) requestList[0]).getUsers().add((User) requestList[1]);
                        Date date=new Date();
                        GamesDB.getInstance().getGameByUsername((String) requestList[0]).setLastMin(date.getMinutes());
                        if( GamesDB.getInstance().getGameByUsername((String) requestList[0]).getUsers().size()== GamesDB.getInstance().getGameByUsername((String) requestList[0]).getCapacity()){
                            try {
                                host.sendMessageToClient(sender,"Started a game ");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }else{
                            try {
                                host.sendMessageToClient(sender,"joined a game ");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }



                    }
                    try {
                        GamesDB.getInstance().update();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if(requestString.equals("updatingPlayersInSession")){
                    String string="";
                    for (User user1 : GamesDB.getInstance().getGameByUsername((String) requestList[0]).getUsers()) {
                        string+=user1.getUsername();
                        if(user1.equals(GamesDB.getInstance().getGameByUsername((String) requestList[0]).getHost())){
                            string+=" (Host) ";
                        }
                        string+="\n";
                    }
                    try {
                        host.sendMessageToClient(sender,string);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if(requestString.equals("getuser")){
                    String username = (String) requestList[0];
                    User user = UsersDB.usersDB.getUserByUsername(username);
                    try {
                        host.sendObjectToClient(sender, user);
                    } catch (
                            IOException e) {
                        throw new RuntimeException(e);
                    }
                }else if(requestString.equals("startGame")){
                    boolean isHost=false;
                    if (GamesDB.getInstance().getGameByUsername((String) requestList[0]).getHost().getUsername().equals((String) requestList[1])){
                    isHost=true;

                    }
                    try {
                        host.sendObjectToClient(sender,isHost);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }else if(requestString.equals("sendFriendReq")){
                    RequestDB.getInstance().addRequest((User) requestList[0],(User) requestList[1]);
                    try {
                        host.sendMessageToClient(sender,"sended");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }else if(requestString.equals("acceptFriendReq")){
                    FriendsDB.getInstance().addFriendForUsers((User) requestList[0],(User) requestList[1]);
                    try {
                        FriendsDB.getInstance().update();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    RequestDB.getInstance().removeRequest((User) requestList[0],(User) requestList[1]);
                    try {
                        host.sendMessageToClient(sender,"accepted");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }else if(requestString.equals("rejectFriendReq")){

                    RequestDB.getInstance().removeRequest((User) requestList[0],(User) requestList[1]);
                    try {
                        host.sendMessageToClient(sender,"rejected");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }else if(requestString.equals("showFriendReq")){

                    ArrayList<User> friends=new ArrayList<>();
                    friends=FriendsDB.getInstance().getFriends().get((User) requestList[0]);
                    try {
                        host.sendObjectToClient(sender,friends);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }
            }
        });
    }
}
