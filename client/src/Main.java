import GUI.Cli;
import GUI.Gui;
import client.Client;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {


        Cli.getInstance().onConnexion("Akitoshi", "localhost", 500);
        String message;
        Scanner scanner = new Scanner(System.in);


        while(true){
            message = scanner.nextLine();
            Cli.getInstance().sendMessage(message);
        }
    }
}
