// Client Node of Simplified Efficient Transmission Hub ( SETH )

package stronghold.model.utils.network.seth;

import stronghold.controller.graphical.ProfileEditController;

import java.io.*;
import java.net.Socket;

public class Client extends NetworkNode{

    private Socket socket;

    public Client(String subnet) throws IOException {
        this.socket = new Socket(subnet,DEFAULT_PORT);
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = new PrintWriter(socket.getOutputStream(), true);
    }

    public Client() throws IOException {
        this(new NetworkNode().findFirstSubnetAvailable());
    }

    public void sendMessageToHost(String message){
        output.println(message);
    }

    public String recieveMessageFromHost(String message){
        char[] dataBuffer = new char[8192];
        try {
            input.read(dataBuffer);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        return String.copyValueOf(dataBuffer);
    }

}
