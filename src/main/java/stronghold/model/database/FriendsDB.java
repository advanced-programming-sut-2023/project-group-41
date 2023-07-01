package stronghold.model.database;

import stronghold.model.components.general.User;
import stronghold.model.components.lobby.Game;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class FriendsDB implements Serializable {
    private HashMap<User, ArrayList<User>> friends;


    public static FriendsDB friendsDB=new FriendsDB();
    private final String path = "src/main/java/stronghold/database/datasets/friends.ser";





    public static FriendsDB getInstance() {
        return friendsDB;
    }
    private FriendsDB(){
        try (
                FileInputStream fileInputStream = new FileInputStream(path);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
        ){
            friends = (HashMap<User, ArrayList<User>>) objectInputStream.readObject();

        } catch (Exception e) {
            friends = new HashMap<>();



        }
    }

    public void addFriendForUsers(User user1, User user2){
        if(!friends.containsKey(user1)){
            friends.put(user1, new ArrayList<>());
        }
        if(!friends.containsKey(user2)){
            friends.put(user2, new ArrayList<>());
        }
        if(!friends.get(user1).contains(user2)){
            friends.get(user1).add(user2);
        }
        if(!friends.get(user2).contains(user1)){
            friends.get(user2).add(user1);
        }

    }

    public void removeFriendFromUsers(User user1, User user2){
        if(friends.get(user1).contains(user2)){
            friends.get(user1).remove(user2);
        }
        if(friends.get(user2).contains(user1)){
            friends.get(user2).remove(user1);
        }
    }

    public HashMap<User, ArrayList<User>> getFriends() {
        return friends;
    }

    public void update() throws IOException {
        try (

                FileOutputStream fileOutputStream = new FileOutputStream(path, false);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){
            objectOutputStream.writeObject(friends);
        }

    }
}
