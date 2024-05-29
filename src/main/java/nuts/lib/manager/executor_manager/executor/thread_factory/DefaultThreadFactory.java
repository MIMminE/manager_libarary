package nuts.lib.manager.executor_manager.executor.thread_factory;

import java.util.concurrent.ThreadFactory;

public class DefaultThreadFactory implements ThreadFactory {
    private final String poolName;
    private int serial = 0;

    public DefaultThreadFactory(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public synchronized Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(poolName + "_" + serial++);
        return thread;
    }
}
