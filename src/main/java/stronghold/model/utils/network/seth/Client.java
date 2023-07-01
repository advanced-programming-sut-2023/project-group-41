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
    private Consumer<String> handleReceivedMessages;

    public Client(String subnet) throws IOException {
        this.socket = new Socket(subnet, DEFAULT_PORT);
        new ObjectOutputStream(socket.getOutputStream()).writeObject(socket.getInetAddress());
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = new PrintWriter(socket.getOutputStream());


        this.handleReceivedMessages = System.out::println;
        this.handleReceivedObjects = (Request obj) -> {
            System.out.println(obj.receivedObject);
        };
        this.readThread = new Thread(() -> {
            while (true) {
                try {
                    Object object = recieveObjectFromHost();
                    if (object != null) {
                        Request request = new Request(object, socket);
                        if (object instanceof String) {
                            System.out.println(1);
                            handleReceivedMessages.accept((String) object);
                        } else {
                            handleReceivedObjects.accept(request);
                        }

                    }
                } catch (
                        Exception ignore) {

                }
            }
        });
//        readThread.start();
    }

    public void setHandleReceivedObjects(Consumer<Request> handleReceivedObjects) {
        this.handleReceivedObjects = handleReceivedObjects;
    }

    public void setHandleReceivedMessages(Consumer<String> handleReceivedMessages) {
        this.handleReceivedMessages = handleReceivedMessages;
    }

    public Client() throws IOException {
        this(new NetworkNode().findFirstSubnetAvailable());
    }

    public void sendMessageToHost(String message) {
        output.println(message);

    }

    public String recieveMessgeFromHost() {
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
            return objectInputStream.readObject();
        } catch (
                IOException e) {

            throw new RuntimeException(e);
        } catch (
                ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
