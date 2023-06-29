// Host Node of Simplified Efficient Transmission Hub ( SETH )

package stronghold.model.utils.network.seth;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Host extends NetworkNode{

    private ServerSocket serverSocket;
    private Socket ioSocket;

    private Thread lighthouseThread;
    private Thread readThread;

    private Consumer<Object> handleReceivedObjects;
    private Consumer<String>  handleReceivedMessages;

    private ArrayList<Socket> clientSockets;

    public Host(boolean initLighthouseThread) throws IOException {
        this.serverSocket = new ServerSocket(DEFAULT_PORT);

        this.handleReceivedMessages = (String str) -> {
            System.out.println(str);
            return;
        };
        this.handleReceivedObjects = (Object obj) -> {
            System.out.println(obj.toString());
            return;
        };

        this.clientSockets = new ArrayList<>();
        this.lighthouseThread = new Thread(() -> {
            while (initLighthouseThread){
                try {
                    acceptClient();
                    System.out.println("ACCEPTING SOCKETS...");

                } catch (
                        Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }
        });
        this.readThread = new Thread(() -> {
            while (initLighthouseThread){
                ArrayList<Socket> currentClients = new ArrayList<>();
                currentClients.addAll(this.clientSockets);
                try {
                    for (Socket socket: currentClients){
                        Object received = recieveObjectFromClient(socket);
                        if(received instanceof String){
                            handleReceivedMessages.accept((String) received);
                        }
                        else{
                            handleReceivedObjects.accept(received);
                        }
                    }
                } catch (
                        IOException e) {
                } catch (
                        ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        this.lighthouseThread.start();
        this.readThread.start();
    }

    public Host() throws IOException {
        this(true);
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setHandleReceivedObjects(Consumer<Object> handleReceivedObjects) {
        this.handleReceivedObjects = handleReceivedObjects;
    }

    public void setHandleReceivedMessages(Consumer<String> handleReceivedMessages) {
        this.handleReceivedMessages = handleReceivedMessages;
    }

    public void acceptClient() throws Exception {
        if(this.readThread.isDaemon())
            this.readThread.wait(200);
        this.clientSockets.add(this.serverSocket.accept());

    }

    public String readMessageFromClient(Socket socket) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String readLine = bufferedReader.readLine();
        if(readLine == null)
            return null;
        bufferedReader.close();
        return readLine;
    }

    public void sendMessageToClient(Socket socket, String Message) throws IOException{
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println(Message);
        printWriter.close();
    }

    public void sendObjectToClient(Socket socket, Object object) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(object);
    }

    public Object recieveObjectFromClient(Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Object recievedObject = objectInputStream.readObject();
        return recievedObject;
    }

    public void killHost() throws IOException {
        for(Socket client: this.clientSockets){
            client.close();
        }
        this.lighthouseThread.stop();
        this.readThread.stop();
        this.serverSocket.close();
    }


}
