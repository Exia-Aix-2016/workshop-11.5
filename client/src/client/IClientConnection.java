package client;

import java.io.IOException;

public interface IClientConnection {

    void closeConnection() throws IOException;
    boolean  isDisconnect();
}
