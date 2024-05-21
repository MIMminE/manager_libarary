package nuts.lib.manager.executor_manager.executor.builder;

import nuts.lib.manager.executor_manager.executor.DefaultThreadFactory;

import java.util.concurrent.*;

public class ScheduledPoolBuilder {

    private String schedulerName = "scheduler";
    private int corePoolSize = 1;
    private ThreadFactory threadFactory = new DefaultThreadFactory(schedulerName);
    private RejectedExecutionHandler executionHandler;


    public ScheduledPoolBuilder schedulerName(String schedulerName) {
        this.schedulerName = schedulerName;
        return this;
    }

    public ScheduledPoolBuilder corePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
        return this;
    }

    public ScheduledPoolBuilder threadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
        return this;
    }

    public ScheduledExecutorService build() {

        return executionHandler == null ?
                new ScheduledThreadPoolExecutor(corePoolSize, threadFactory)
                :
                new ScheduledThreadPoolExecutor(corePoolSize, threadFactory, executionHandler);
    }
}
