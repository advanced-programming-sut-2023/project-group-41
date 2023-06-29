package stronghold.model.components.chatrooms;

import stronghold.model.components.general.User;

import java.util.HashMap;

public class Message {
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
        room.addMessage(this);
        this.date = date;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public HashMap<Reaction, Integer> getReactions() {
        return reactions;
    }

    public void incReactionByOne(Reaction reaction) {
        reactions.put(reaction, reactions.get(reaction) + 1);
    }

    public int getReaction(Reaction reaction) {
        return reactions.get(reaction);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void del(){
        room.getMessages().remove(this);
    }
}
