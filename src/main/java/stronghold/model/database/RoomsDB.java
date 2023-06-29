package stronghold.model.database;

import stronghold.model.components.chatrooms.Room;
import stronghold.model.components.general.User;

import java.util.ArrayList;
import java.util.List;

public class RoomsDB {
    private Room pubicChat;
    private ArrayList<Room> rooms;
    private static RoomsDB instanceRoomsDB = new RoomsDB();

    public static RoomsDB getInstance() {
        return instanceRoomsDB;
    }

    private RoomsDB(){
        rooms = new ArrayList<>();

        List<User> allUsers = UsersDB.usersDB.getUsers();
        pubicChat = new Room(null, "publicRoom");
        for (User user : allUsers) {
            pubicChat.addUser(user);
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
}
