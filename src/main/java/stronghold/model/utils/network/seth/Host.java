// Host Node of Simplified Efficient Transmission Hub ( SETH )

package stronghold.model.utils.network.seth;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Host{

    private ServerSocket serverSocket;
    private Thread lighthouseThread;
    private ArrayList<Socket> clientSockets;

    public Host(boolean initLighthouseThread) throws IOException {
        this.serverSocket = new ServerSocket(12345);
        this.clientSockets = new ArrayList<>();
        this.lighthouseThread = new Thread(() -> {
            while (initLighthouseThread){
                try {
                    this.acceptClient();
                } catch (
                        IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("ACCEPTING SOCKETS...");

                try {
                    Thread.sleep(200);
                } catch (
                        InterruptedException e) {
                    System.out.println(1);
                    
                }
            }
        });
        this.lighthouseThread.start();
    }

    public Host() throws IOException {
        this(true);
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void acceptClient() throws IOException {
        this.clientSockets.add(serverSocket.accept());
    }

    public void killHost() throws IOException {
        for(Socket client: this.clientSockets){
            client.close();
        }
        this.lighthouseThread.stop();
        this.serverSocket.close();
    }
}
