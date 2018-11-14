import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class Client implements Runnable, Observer {
    private Socket socket;
    BufferedReader in;
    PrintWriter out;
    ServerSocketListener broadcast;

    Client(Socket socket, ServerSocketListener broadcast) throws IOException {
        this.socket = socket;
        this.broadcast = broadcast;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter((socket.getOutputStream()));
        broadcast.addObserver(this);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = this.in.readLine();
                System.out.println(message);
                this.broadcast.emit(message);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void update(Observable o, Object arg) {
        String message = (String)arg;
        this.out.write(message+"\r\n");
        this.out.flush();
    }
}
