package nuts.lib.manager.executor_manager.executor.builder;

import nuts.lib.manager.executor_manager.executor.DefaultThreadFactory;

import java.util.concurrent.*;

public class CachedPoolBuilder {

    private String executorName = "cached_executor";
    private long keepAliveTime = 60L;
    private RejectedExecutionHandler executionHandler;

    public CachedPoolBuilder executorName(String executorName) {
        this.executorName = executorName;
        return this;
    }

    public CachedPoolBuilder keepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
        return this;
    }

    public CachedPoolBuilder executionHandler(RejectedExecutionHandler executionHandler) {
        this.executionHandler = executionHandler;
        return this;
    }

    public ExecutorService build() {

        return executionHandler == null ?
                new ThreadPoolExecutor(0, Integer.MAX_VALUE, keepAliveTime, TimeUnit.SECONDS,
                        new SynchronousQueue<Runnable>(),
                        new DefaultThreadFactory(executorName))
                :
                new ThreadPoolExecutor(0, Integer.MAX_VALUE, keepAliveTime, TimeUnit.SECONDS,
                        new SynchronousQueue<Runnable>(),
                        new DefaultThreadFactory(executorName), executionHandler);
    }
}
