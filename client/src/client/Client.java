package client;

import GUI.IDisplay;
import GUI.IRead;
import GUI.ISend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;

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
        } catch (IOException e) {
            this.printer.displayMessage("unable to connect !");
        }
        try {
            this.writer = new PrintWriter(this.clientSocket.getOutputStream());
            this.reader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        while (!this.clientSocket.isClosed()){
            this.readMessage().ifPresent(this.printer::displayMessage);
        }

    }
    @Override
    public synchronized void closeConnection() throws IOException {
        this.clientSocket.close();
    }

   public synchronized boolean  isDisconnect(){
        return this.clientSocket.isClosed();
    }


    @Override
    public void sendMessage(String msg) {
        if(this.clientSocket.isClosed()) return;
        this.writer.write(this.userName + " : " + msg + "\r\n");
        this.writer.flush();
    }

    @Override
    public Optional<String> readMessage() {
        if(this.clientSocket.isClosed()) return Optional.empty();
        try {
            if(!reader.ready()) return Optional.empty();
            return Optional.of(this.reader.readLine());
        } catch (IOException e) {
            System.out.println(e.toString());
            return Optional.empty();
        }
    }
}
