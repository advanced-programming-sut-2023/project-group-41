// Host Node of Simplified Efficient Transmission Hub ( SETH )

package stronghold.model.utils.network.seth;


import javafx.beans.binding.ObjectExpression;
import stronghold.model.components.general.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Host extends NetworkNode{

    private ServerSocket serverSocket;
    private Socket ioSocket;

    private Thread lighthouseThread;
    private Thread readThread;

    private ArrayList<Socket> clientSockets;

    public Host(boolean initLighthouseThread) throws IOException {
        this.serverSocket = new ServerSocket(DEFAULT_PORT);

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
                        if(received instanceof User){
                            User user = (User) received;
                            System.out.println(user.getUsername());
                            
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

    public void acceptClient() throws Exception {
        if(this.readThread.isDaemon())
            this.readThread.wait(200);
        this.clientSockets.add(this.serverSocket.accept());

    }

    public void readMessageFromClient(Socket socket) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String readLine = bufferedReader.readLine();
        if(readLine == null)
            return;
        System.out.println(readLine);
        bufferedReader.close();
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
