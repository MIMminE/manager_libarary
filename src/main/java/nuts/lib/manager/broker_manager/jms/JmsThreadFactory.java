package nuts.lib.manager.broker_manager.jms;

import java.util.concurrent.ThreadFactory;

public class JmsThreadFactory implements ThreadFactory {

    private final String poolName = "JmsListener";
    private int threadCount = 0;


    @Override
    public synchronized Thread newThread(Runnable r) {

        Thread thread = new Thread(r);
        thread.setName(poolName + "_" + threadCount++);
        return thread;
    }
}
