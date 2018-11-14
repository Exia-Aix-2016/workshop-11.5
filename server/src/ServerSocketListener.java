import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerSocketListener extends Observable implements Runnable, Emit {

    private ServerSocket serverSocket;
    ThreadPoolExecutor clientPool = new ClientPool();

    ServerSocketListener(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                Client client = new Client(socket, this);
                clientPool.submit(client);
            } catch (Exception e) {
                System.out.print(e);
            }
        }
    }

    @Override
    public void emit(String message) {
        this.setChanged();
        this.notifyObservers(message);
    }
}
