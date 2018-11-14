import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerSocketListener implements Runnable {

    private ServerSocket serverSocket;
    ThreadPoolExecutor clientPool = new ClientPool();

    Observable broadcast = new Observable();

    ServerSocketListener(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                Client client = new Client(socket, broadcast);
                clientPool.submit(client);
            } catch (Exception e) {
                System.out.print(e);
            }
        }
    }
}
