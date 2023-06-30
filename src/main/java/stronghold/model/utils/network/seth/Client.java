// Client Node of Simplified Efficient Transmission Hub ( SETH )

package stronghold.model.utils.network.seth;

import stronghold.controller.graphical.ProfileEditController;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class Client extends NetworkNode {

    private Socket socket;
    private Thread readThread;
    private Consumer<Request> handleReceivedObjects;
    private Consumer<Request> handleReceivedMessages;

    public Client(String subnet) throws IOException {
        this.socket = new Socket(subnet, DEFAULT_PORT);
        new ObjectOutputStream(socket.getOutputStream()).writeObject(socket.getInetAddress());
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = new PrintWriter(socket.getOutputStream(), true);


        this.handleReceivedMessages = (Request message) -> {
            System.out.println(message.receivedObject.toString());
            return;
        };
        this.handleReceivedObjects = (Request obj) -> {
            System.out.println(obj.receivedObject);
            return;
        };
        this.readThread = new Thread(() -> {
            while (true) {
                try {
                    Object object = recieveObjectFromHost();
                    if(object != null){
                        if(!socket.isClosed()){
                            Request request = new Request(object, socket);
                            if(object instanceof String){
                                handleReceivedMessages.accept(request);
                            }
                            else {
                                handleReceivedObjects.accept(request);
                            }
                        }
                    }
                } catch (
                        Exception ignore) {

                }
                try {
                    Thread.sleep(30);
                } catch (
                        InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        readThread.start();
    }

    public void setHandleReceivedObjects(Consumer<Request> handleReceivedObjects) {
        this.handleReceivedObjects = handleReceivedObjects;
    }

    public void setHandleReceivedMessages(Consumer<Request> handleReceivedMessages) {
        this.handleReceivedMessages = handleReceivedMessages;
    }

    public Client() throws IOException {
        this(new NetworkNode().findFirstSubnetAvailable());
    }

    public void sendMessageToHost(String message) {
        output.println(message);

    }

    public String recieveMessageFromHost() {
        String dataBuffer;
        try {
            dataBuffer = input.readLine();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        return dataBuffer;
    }

    public void sendObjectToServer(Object object) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(object);
        } catch (
                IOException e) {

        }
    }

    public Object recieveObjectFromHost() {
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
