import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(500);
        Runnable socketListener = new ServerSocketListener(serverSocket);
        new Thread(socketListener).start();
        System.out.println("Server started");
    }
}
