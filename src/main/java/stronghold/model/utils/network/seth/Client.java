// Client Node of Simplified Efficient Transmission Hub ( SETH )

package stronghold.model.utils.network.seth;

import stronghold.controller.graphical.ProfileEditController;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;

public class Client extends NetworkNode{

    private Socket socket;

    private Consumer<Object> handleReceivedObjects;
    private Consumer<String>  handleReceivedMessages;

    public Client(String subnet) throws IOException {
        this.socket = new Socket(subnet,DEFAULT_PORT);
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = new PrintWriter(socket.getOutputStream(), true);
        this.handleReceivedMessages = (String str) -> {
            System.out.println(str);
        };
        this.handleReceivedObjects = (Object obj) -> {
            System.out.println(obj.toString());
        };
    }

    public void setHandleReceivedObjects(Consumer<Object> handleReceivedObjects) {
        this.handleReceivedObjects = handleReceivedObjects;
    }

    public void setHandleReceivedMessages(Consumer<String> handleReceivedMessages) {
        this.handleReceivedMessages = handleReceivedMessages;
    }

    public Client() throws IOException {
        this(new NetworkNode().findFirstSubnetAvailable());
    }

    public void sendMessageToHost(String message){
        output.println(message);

    }

    public String recieveMessageFromHost(){
        String dataBuffer;
        try {
            dataBuffer = input.readLine();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        return dataBuffer;
    }

    public void sendObjectToServer(Object object){
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(object);
        } catch (
                IOException e) {

            
        }
    }

    public Object recieveObjectFromHost(){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object recievedObejct = objectInputStream.readObject();
            return recievedObejct;
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        } catch (
                ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
