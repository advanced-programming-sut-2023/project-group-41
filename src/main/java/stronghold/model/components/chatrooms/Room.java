package stronghold.model.components.chatrooms;

import stronghold.model.components.general.User;
import stronghold.model.database.RoomsDB;

import java.util.ArrayList;

public class Room {
    private String name;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Message> messages = new ArrayList<>();

    public Room(User user, String name) {
        this.name = name;
        users.add(user);
        if (!name.equals("publicRoom"))
            RoomsDB.getInstance().addRoom(this);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
