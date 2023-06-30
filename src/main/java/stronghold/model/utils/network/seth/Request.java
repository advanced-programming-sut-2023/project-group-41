package stronghold.model.utils.network.seth;

import java.net.Socket;

public class Request {
    Object receivedObject;
    Socket sender;

    public Request(Object receivedObject, Socket sender) {
        this.receivedObject = receivedObject;
        this.sender = sender;
    }

    public Object getReceivedObject() {
        return receivedObject;
    }

    public void setReceivedObject(Object receivedObject) {
        this.receivedObject = receivedObject;
    }

    public Socket getSender() {
        return sender;
    }

    public void setSender(Socket sender) {
        this.sender = sender;
    }
}
