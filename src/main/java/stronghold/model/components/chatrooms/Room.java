package stronghold.model.components.chatrooms;

import stronghold.model.components.general.User;
import stronghold.model.database.RoomsDB;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Room implements Serializable {
    private String name;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Message> messages = new ArrayList<>();

    public Room(User user, String name) {
        this.name = name;
        users.add(user);
        if (!name.equals("publicRoom")) {
            RoomsDB.getInstance().addRoom(this);
            RoomsDB.getInstance().update();
        }
    }

    public Message getMessageByObj(Object obj) {
        for (Message message1 : messages) {
            if (message1.equals(obj))
                return message1;
        }
        return null;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) throws IOException {
        users.add(user);
        if (!name.equals("publicRoom"))
            RoomsDB.getInstance().update();
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) throws IOException {
        messages.add(message);
        RoomsDB.getInstance().update();
    }

    public void setUsers(ArrayList<User> users) throws IOException {
        this.users = users;
        RoomsDB.getInstance().update();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IOException {
        this.name = name;
        RoomsDB.getInstance().update();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(name, room.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
