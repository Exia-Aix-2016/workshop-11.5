package client;

import GUI.IDisplay;
import GUI.IRead;
import GUI.ISend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client implements Runnable, ISend, IRead, IClientConnection {

    private Socket clientSocket;
    private String userName;
    private boolean run = false;
    private static Client instance;
    private PrintWriter writer;
    private BufferedReader reader;
    private String address;
    private int port;
    private IDisplay printer;
    private Cypher cypher = new Base64Cypher();
    private Pattern messagePattern = Pattern.compile("(\\w*)\\s:\\s(.*)");

    public Client(IDisplay printer, final String userName, final String address, final int port){
        this.printer = printer;
        this.address = address;
        this.port = port;
        this.userName = userName;
    }

    @Override
    public void run() {

        try {
            this.clientSocket = new Socket(this.address, this.port);
            this.writer = new PrintWriter(this.clientSocket.getOutputStream());
            this.reader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        } catch (IOException e) {
            this.printer.displayMessage("unable to connect !");
            return;

        }

        while (!this.clientSocket.isClosed()){
           this.readMessage().ifPresent(this.printer::displayMessage);
        }
    }
    @Override
    public synchronized void closeConnection() throws IOException {
        this.clientSocket.close();
    }

   public synchronized Optional<Boolean>  isDisconnect(){
        if(this.clientSocket == null) return Optional.empty();
        return Optional.of(this.clientSocket.isClosed());
    }


    @Override
    public void sendMessage(String msg) {
        String message = this.cypher.encode(msg);
        this.writer.write(this.userName + " : " + message + "\r\n");
        this.writer.flush();
    }

    @Override
    public Optional<String> readMessage() {
        try {
            if(!reader.ready()) return Optional.empty();
            String message = this.reader.readLine().replaceAll("[\n\r]", "");
            Matcher m = this.messagePattern.matcher(message);
            m.find();
            String user = m.group(1);
            String text = m.group(2);
            return Optional.of(user + " : " +this.cypher.decode(text));
        } catch (IOException e) {
            System.out.println(e.toString());
            return Optional.empty();
        }
    }
}
