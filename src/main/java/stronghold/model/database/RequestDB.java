package stronghold.model.database;



import stronghold.model.components.general.User;
import stronghold.model.components.lobby.Game;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class RequestDB implements Serializable {

    private ArrayList<ArrayList<User>>requests;

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
            requests = (ArrayList<ArrayList<User>>) objectInputStream.readObject();

        } catch (Exception e) {
            requests = new ArrayList<>();



        }
    }

    public ArrayList<ArrayList<User>> getRequests() {
        return requests;
    }

    public void addRequest(User user, User user2) {
        ArrayList<User> adder= new ArrayList<>();
        adder.add(user);
        adder.add(user2);
        requests.add(adder);
        try {
            RequestDB.getInstance().update();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void removeRequest(User user,User user2) {
        requests.removeIf(request -> request.get(0).equals(user) && request.get(1).equals(user2));


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

