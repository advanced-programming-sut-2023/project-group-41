package stronghold.model.components.chatrooms;

import stronghold.model.components.general.User;
import stronghold.model.database.RoomsDB;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public class Message implements Serializable {
    Room room;
    User user;
    String text;
    String date;

    Boolean sendMessage = false;
    Boolean seenMassage = false;
    HashMap<Reaction, Integer> reactions = new HashMap<>();

    {
        reactions.put(Reaction.LAUGH, 0);
        reactions.put(Reaction.PEE, 0);
        reactions.put(Reaction.HEART, 0);
    }

    public Message(Room room, User user, String text, String date) {
        this.room = room;
        this.user = user;
        this.text = text;
        try {
            room.addMessage(this);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        this.date = date;
        RoomsDB.getInstance().update();
    }

    public Message(User user, String text, String date) {
        this.room = null;
        this.user = user;
        this.text = text;
        this.date = date;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
        RoomsDB.getInstance().update();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        RoomsDB.getInstance().update();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        RoomsDB.getInstance().update();
    }

    public HashMap<Reaction, Integer> getReactions() {
        return reactions;
    }

    public void incReactionByOne(Reaction reaction) {
        reactions.put(reaction, reactions.get(reaction) + 1);
        RoomsDB.getInstance().update();
    }

    public int getReaction(Reaction reaction) {
        return reactions.get(reaction);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;RoomsDB.getInstance().update();
    }

    public void del(){
        room.getMessages().remove(this);
        RoomsDB.getInstance().update();
    }

    public Boolean getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(Boolean sendMessage) {
        this.sendMessage = sendMessage;
    }

    public Boolean getSeenMassage() {
        return seenMassage;
    }

    public void setSeenMassage(Boolean seenMassage) {
        this.seenMassage = seenMassage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(user, message.user) && Objects.equals(text, message.text) && Objects.equals(date, message.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, text, date);
    }
}