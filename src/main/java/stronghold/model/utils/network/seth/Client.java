// Client Node of Simplified Efficient Transmission Hub ( SETH )

package stronghold.model.utils.network.seth;

import java.io.*;
import java.net.Socket;

public class Client {

    protected final static int DEFAULT_PORT = 12345;

    private Socket socket;

    public Client() throws IOException {
        this.socket = new Socket("localhost",DEFAULT_PORT);

    }

    public void testConnection() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        out.println(1);
    }


}
