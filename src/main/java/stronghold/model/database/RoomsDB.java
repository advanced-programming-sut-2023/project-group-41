package stronghold.model.database;

import stronghold.model.components.chatrooms.Room;
import stronghold.model.components.general.User;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RoomsDB implements Serializable {
    private Room pubicChat;
    private ArrayList<Room> rooms;
    private static RoomsDB instanceRoomsDB = new RoomsDB();
    private String path = "src/main/java/stronghold/database/datasets/chats.ser";

    public static RoomsDB getInstance() {
        return instanceRoomsDB;
    }

    private RoomsDB(){
        try (
                FileInputStream fileInputStream = new FileInputStream(path);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                ){
            try {
                instanceRoomsDB = (RoomsDB) objectInputStream.readObject();
            } catch (IOException e) {
                System.out.println("dd");
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                System.out.println("psdca");
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            System.out.println("error in file input");
            rooms = new ArrayList<>();
            List<User> allUsers = UsersDB.usersDB.getUsers();
            pubicChat = new Room(null, "publicRoom");
            for (User user : allUsers) {
                pubicChat.addUser(user);
            }
        }
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Room getPubicChat() {
        return pubicChat;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<Room> getUserRooms(User user) {
        ArrayList<Room> userRooms = new ArrayList<>();
        for (Room room : instanceRoomsDB.getRooms()) {
            if (room.getUsers().contains(user))
                userRooms.add(room);
        }
        return userRooms;
    }

    public void update(){
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(path);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);){
            objectOutputStream.writeObject(instanceRoomsDB);
        } catch (Exception e){
            System.out.println("error in file output");
        }

    }

    public static void main(String[] args) {
        System.out.println(RoomsDB.getInstance().getRooms().get(0).getName());
    }
}
