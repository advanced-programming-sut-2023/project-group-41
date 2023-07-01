package stronghold.model.database;

import stronghold.model.components.chatrooms.Room;
import stronghold.model.components.general.User;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RoomsDB implements Serializable {
    private ArrayList<Room> rooms;
    private static RoomsDB instanceRoomsDB = new RoomsDB();
    private final String path = "src/main/java/stronghold/database/datasets/chats.ser";

    public static RoomsDB getInstance() {
        return instanceRoomsDB;
    }

    private RoomsDB(){
        try (
                FileInputStream fileInputStream = new FileInputStream(path);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
        ){
            rooms = (ArrayList<Room>) objectInputStream.readObject();
        } catch (Exception e) {
            rooms = new ArrayList<>();
            List<User> allUsers = UsersDB.usersDB.getUsers();
            Room pubicChat = null;
            pubicChat = new Room(null, "publicRoom");
            for (User user : allUsers) {
                try {
                    pubicChat.addUser(user);
                } catch (
                        IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            rooms.add(pubicChat);
        }
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Room getPubicChat() {
        return rooms.get(0);
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<Room> getUserRooms(User user) {
        ArrayList<Room> userRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getUsers().contains(user))
                userRooms.add(room);
        }
        return userRooms;
    }

    public void update() {
        try (

                FileOutputStream fileOutputStream = new FileOutputStream(path, false);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){
            objectOutputStream.writeObject(rooms);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}