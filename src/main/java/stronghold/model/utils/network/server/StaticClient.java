package stronghold.model.utils.network.server;

import stronghold.model.utils.network.seth.Client;

import java.io.IOException;

public class StaticClient {
    static Client client;

    public StaticClient() throws IOException {
        client = new Client();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
