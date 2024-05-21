package nuts.lib.manager.executor_manager.executor;

import nuts.lib.manager.executor_manager.executor.builder.CachedPoolBuilder;
import nuts.lib.manager.executor_manager.executor.builder.FixedPoolBuilder;
import nuts.lib.manager.executor_manager.executor.builder.ScheduledPoolBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

public abstract class ExecutorBuilder {

    public static FixedPoolBuilder fixedExecutor() {
        return new FixedPoolBuilder();
    }

    public static CachedPoolBuilder cachedExecutor() {
        return new CachedPoolBuilder();
    }

    public static ScheduledPoolBuilder scheduledExecutor() {
        return new ScheduledPoolBuilder();
    }

    public static ExecutorService newFixedExecutor(int nThread) {
        return Executors.newFixedThreadPool(nThread, new DefaultThreadFactory("fixed_executor"));
    }

    public static ExecutorService newFixedExecutor(int nThread, String poolName) {
        return Executors.newFixedThreadPool(nThread, new DefaultThreadFactory(poolName));
    }

    public static ExecutorService newFixedExecutor(int nThread, ThreadFactory threadFactory) {
        return Executors.newFixedThreadPool(nThread, threadFactory);
    }

    public static ExecutorService newCachedExecutor() {
        return Executors.newCachedThreadPool(new DefaultThreadFactory("cached_executor"));
    }

    public static ExecutorService newCachedExecutor(String poolName) {
        return Executors.newCachedThreadPool(new DefaultThreadFactory(poolName));
    }

    public static ExecutorService newCachedExecutor(ThreadFactory threadFactory) {
        return Executors.newCachedThreadPool(threadFactory);
    }

    public static ExecutorService newWorkStealingExecutor() {
        return Executors.newWorkStealingPool();
    }

    public static ExecutorService newWorkStealingExecutor(int parallelism) {
        return Executors.newWorkStealingPool(parallelism);
    }

    public static ScheduledExecutorService newSingleThreadScheduledExecutor() {
        return Executors.newSingleThreadScheduledExecutor(new DefaultThreadFactory("single_thread_scheduler"));
    }

    public static ScheduledExecutorService newSingleThreadScheduledExecutor(String poolName) {
        return Executors.newSingleThreadScheduledExecutor(new DefaultThreadFactory(poolName));
    }

    public static ScheduledExecutorService newSingleThreadScheduledExecutor(ThreadFactory threadFactory) {
        return Executors.newSingleThreadScheduledExecutor(threadFactory);
    }

    public static ScheduledExecutorService newScheduledExecutor(int corePoolSize) {
        return Executors.newScheduledThreadPool(corePoolSize, new DefaultThreadFactory("scheduler"));
    }

    public static ScheduledExecutorService newScheduledExecutor(int corePoolSize, String poolName) {
        return Executors.newScheduledThreadPool(corePoolSize, new DefaultThreadFactory(poolName));
    }

    public ScheduledExecutorService newScheduledExecutor(int corePoolSize, ThreadFactory threadFactory) {
        return Executors.newScheduledThreadPool(corePoolSize, threadFactory);
    }

}
