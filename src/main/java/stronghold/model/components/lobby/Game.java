package stronghold.model.components.lobby;

import stronghold.model.components.general.User;
import stronghold.model.database.GamesDB;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public   class Game implements Serializable {
    private String title;
    private int capacity;
    private boolean isPrivate;
    private User host;
    private ArrayList<User> users;
    private int lastMin;

    public int getLastMin() {
        return lastMin;
    }

    public void setLastMin(int lastMin) {
        this.lastMin = lastMin;
    }

    public Game(String title, int capacity, boolean isPrivate, User host) {
        this.title = title;
        this.capacity = capacity;
        this.isPrivate = isPrivate;
        this.host = host;
        this.users=new ArrayList<>();
        Date date=new Date();
        this.lastMin=date.getMinutes();
        users.add(host);
        GamesDB.getInstance().getGames().add(this);
        try {
            GamesDB.getInstance().update();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public String getTitle() {
        return title;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public User getHost() {
        return host;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(title, game.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    public void setHost(User host) {
        this.host = host;
    }
}