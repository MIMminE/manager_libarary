package nuts.lib.manager.executor_manager.executor.builder;

import nuts.lib.manager.executor_manager.executor.thread_factory.DefaultThreadFactory;

import java.util.concurrent.*;

public class FixedPoolBuilder {

    private String executorName = "fixed_executor";
    private int nThread = 5;
    private long keepAliveTime = 0L;
    private RejectedExecutionHandler executionHandler;

    public FixedPoolBuilder executorName(String executorName) {
        this.executorName = executorName;
        return this;
    }

    public FixedPoolBuilder nThreads(int nThread) {
        this.nThread = nThread;
        return this;
    }

    public FixedPoolBuilder keepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
        return this;
    }

    public FixedPoolBuilder executionHandler(RejectedExecutionHandler executionHandler) {
        this.executionHandler = executionHandler;
        return this;
    }

    public ExecutorService build() {

        return executionHandler == null ?
                new ThreadPoolExecutor(nThread, nThread, keepAliveTime, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>(),
                        new DefaultThreadFactory(executorName))
                :
                new ThreadPoolExecutor(nThread, nThread, keepAliveTime, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>(),
                        new DefaultThreadFactory(executorName), executionHandler);
    }
}
