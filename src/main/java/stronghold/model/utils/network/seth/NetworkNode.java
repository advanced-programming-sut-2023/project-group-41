package stronghold.model.utils.network.seth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkNode {
    protected final static int DEFAULT_PORT = 12345;
    protected BufferedReader input;
    protected PrintWriter output;

    public ArrayList<String> scanForActiveSubnets(){
        String baseSubnet = "192.168.1.";
        ArrayList<String> activeIPsInLAN = new ArrayList<>();
        for(int i = 0; i < 256; i++){
            try {
                Socket socket = new Socket(baseSubnet+i, DEFAULT_PORT);
                activeIPsInLAN.add(baseSubnet+i);
            } catch (
                    IOException e) {
                continue;
            }
        }
        return activeIPsInLAN;
    }
}
