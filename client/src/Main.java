import GUI.Cli;
import GUI.Gui;
import client.Client;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private  static Boolean run = false;
    public static void main(String[] args) throws IOException, InterruptedException {

        if(args.length < 1) return;

        Cli.getInstance().onConnexion(args[0], "localhost", 500);
        String message;
        Scanner scanner = new Scanner(System.in);



        while(!run){

            Cli.getInstance().getClient().isDisconnect().ifPresent(Main::setRun);

            message = scanner.nextLine();
            Cli.getInstance().sendMessage(message);
        }
    }

    private static void setRun(Boolean run){
        run = run;
    }
}
