package GUI;

public class Cli extends Gui {


    private static Cli ourInstance = new Cli();

    public static Cli getInstance() {
        return ourInstance;
    }

    private Cli() {
    }

    @Override
    public void sendMessage(String msg) {
        this.client.sendMessage(msg);
    }
    @Override
    public void displayMessage(String msg) {
        System.out.println(msg);
    }
}
