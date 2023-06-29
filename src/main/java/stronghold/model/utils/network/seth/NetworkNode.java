package stronghold.model.utils.network.seth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkNode {
    protected final static int DEFAULT_PORT = 12345;
    protected BufferedReader input;
    protected PrintWriter output;

    public ArrayList<String> scanForActiveSubnets(){
        String baseSubnet = "192.168.1.";
        ArrayList<String> activeIPsInLAN = new ArrayList<>();

        for (int i = 1; i <= 255; i++){

            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(baseSubnet + i, DEFAULT_PORT), 40);
                socket.close();
                activeIPsInLAN.add(baseSubnet + i);
                
            } catch (IOException ignored) {
                // Connection failed, ignore and continue
            }
        }

        return activeIPsInLAN;
    }

    public String findFirstSubnetAvailable(){
        String baseSubnet = "192.168.1.";

        for (int i = 1; i <= 254; i++){

            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(baseSubnet + i, DEFAULT_PORT), 40);
                new ObjectOutputStream(socket.getOutputStream()).writeObject(socket.getInetAddress());
                socket.close();
                return baseSubnet + i;

            } catch (IOException ignored) {
                // Connection failed, ignore and continue
            }
        }
        return null;
    }

}
