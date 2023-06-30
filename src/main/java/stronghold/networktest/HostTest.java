package stronghold.networktest;

import javafx.application.Application;
import javafx.stage.Stage;
import stronghold.model.components.general.User;
import stronghold.model.utils.network.authenticator.AuthenticatorServer;
import stronghold.model.utils.network.seth.Host;

import java.io.IOException;
import java.net.Socket;

public class HostTest{


    public static void main(String[] args) throws IOException {
        AuthenticatorServer.setHost(new Host());
        AuthenticatorServer.runAuthenticator();
    }
}
