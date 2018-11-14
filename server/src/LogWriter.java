import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class LogWriter implements Logger {
    private static LogWriter ourInstance = new LogWriter();
    public static String  fileName = "Log.txt";

    public static LogWriter getInstance() {
        return ourInstance;
    }

    @Override
    public void log(String message) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            Date date = new Date();
            writer.append(date.toString());
            writer.append(" ");
            writer.append(message);
            writer.append("\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Logger io error");
        }
    }
}
