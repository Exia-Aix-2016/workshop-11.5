import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ClientPool extends ThreadPoolExecutor {

    Logger logger = LogWriter.getInstance();

    ClientPool() {
        super(0, Integer.MAX_VALUE,60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        logger.log("Client connected");
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        logger.log("Client disconnected");
        super.afterExecute(r, t);
    }
}
