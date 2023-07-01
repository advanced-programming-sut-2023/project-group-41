package stronghold.model.database;

import stronghold.model.components.chatrooms.Room;
import stronghold.model.components.general.User;
import stronghold.model.components.lobby.Game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GamesDB implements Serializable {
    private   ArrayList<Game>games;
    private static GamesDB instanceGameDB=new GamesDB();
    private final String path = "src/main/java/stronghold/database/datasets/games.ser";
    public static GamesDB getInstance() {
        return instanceGameDB;
    }
    private GamesDB(){
        try (
                FileInputStream fileInputStream = new FileInputStream(path);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
        ){
            games = (ArrayList<Game>) objectInputStream.readObject();
        } catch (Exception e) {
            games = new ArrayList<>();


        }
    }

    public ArrayList<Game> getGames() {
        return games;
    }
    public  Game getGameByUsername(String username){
        for (Game game : games) {
            if(game.getTitle().equals(username)){
                return game;

            }
        }
        return null;
    }
    public void update() throws IOException {
        try (

                FileOutputStream fileOutputStream = new FileOutputStream(path, false);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){
            objectOutputStream.writeObject(games);
        }

    }

    public static void main(String[] args) {
         //User user=new User("Sadf","ASdf","sdf","ASdfsdf",2,"23234","WEr");
        //Game game=new Game("Asd",12,true,user);
        //System.out.println(GamesDB.getInstance().getGames().get(0).getTitle());
    }
}
