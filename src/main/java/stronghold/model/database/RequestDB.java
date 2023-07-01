package stronghold.model.database;



import stronghold.model.components.general.User;
import stronghold.model.components.lobby.Game;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class RequestDB implements Serializable {

    private HashMap<User,User>requests;

    public static RequestDB requestDB=new RequestDB();
    private final String path = "src/main/java/stronghold/database/datasets/requests.ser";





    public static RequestDB getInstance() {
        return requestDB;
    }
    private RequestDB(){
        try (
                FileInputStream fileInputStream = new FileInputStream(path);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
        ){
            requests = (HashMap<User, User>) objectInputStream.readObject();

        } catch (Exception e) {
            requests = new HashMap<>();



        }
    }

    public HashMap<User, User> getRequests() {
        return requests;
    }
    public void addRequest(User user,User user2) {
        requests.put(user,user2);
        try {
            RequestDB.getInstance().update();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void removeRequest(User user,User user2) {
        if(requests.get(user)!=null)
            requests.remove(user);
        else if(requests.get(user2)!=null)
            requests.remove(user2);
        else
            return;
        try {
            RequestDB.getInstance().update();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void update() throws IOException {
        try (

                FileOutputStream fileOutputStream = new FileOutputStream(path, false);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){
            objectOutputStream.writeObject(requests);
        }

    }
}

