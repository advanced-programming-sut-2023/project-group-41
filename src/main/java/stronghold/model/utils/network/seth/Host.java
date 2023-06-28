// Host Node of Simplified Efficient Transmission Hub ( SETH )

package stronghold.model.utils.network.seth;


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
                        IOException e) {
                    throw new RuntimeException(e);
                }


                try {
                    Thread.sleep(200);
                } catch (
                        InterruptedException ignored) {
                    
                }
            }
        });
        this.readThread = new Thread(() -> {
            while (initLighthouseThread){
                try {
                    for (Socket socket: this.clientSockets){
                        readMessageFromClient(socket);
                    }
                } catch (
                        IOException e) {
                    throw new RuntimeException(e);
                }


                try {
                    Thread.sleep(200);
                } catch (
                        InterruptedException ignored) {

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

    public void acceptClient() throws IOException {
        this.clientSockets.add(this.serverSocket.accept());
    }

    public void readMessageFromClient(Socket socket) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String readLine = bufferedReader.readLine();
        if(readLine == null)
            return;
        System.out.println(readLine);
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
