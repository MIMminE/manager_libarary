package nuts.lib.manager.executor_manager.executor.thread_factory;

import java.util.concurrent.ThreadFactory;

public class UncaughtExceptionThreadFactory implements ThreadFactory {
    private final String poolName;
    private final Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    private int serial = 0;

    public UncaughtExceptionThreadFactory(String poolName, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.poolName = poolName;
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
    }

    @Override
    public synchronized Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(poolName + "_" + serial++);
        thread.setUncaughtExceptionHandler(this.uncaughtExceptionHandler);
        return thread;
    }
}
