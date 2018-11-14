package client;

import java.io.IOException;
import java.util.Optional;

public interface IClientConnection {

    void closeConnection() throws IOException;
    Optional<Boolean> isDisconnect();
}
