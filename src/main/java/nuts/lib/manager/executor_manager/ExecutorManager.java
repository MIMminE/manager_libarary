package nuts.lib.manager.executor_manager;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * It has a built-in {@link ExecutorService} and executes tasks declared as {@link Runnable} and {@link Callable}. <p>
 * Tasks can be executed immediately or delayed.
 * <p>
 * The methods used for submitting deferred work are as follows:
 * <p>
 * Runnable Task <p>
 * {@link ExecutorManager#deferredSubmit(Runnable)}, {@link ExecutorManager#deferredSubmit(List)}
 * <p>
 * Callable Task <p>
 * {@link ExecutorManager#deferredSubmitCallable(Callable)}, {@link ExecutorManager#deferredSubmitCallable(List)}
 *
 * @author nuts
 * @since 2024. 05. 23
 */
public class ExecutorManager {

    private ExecutorService executorService;
    private Supplier<ExecutorService> deferredExecutorService;
    private final BlockingQueue<Runnable> blockingRunnableQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Callable<?>> blockingCallableQueue = new LinkedBlockingQueue<>();


    public ExecutorManager(ExecutorService executorService) {
        this.executorService = executorService;
    }

    /**
     * A constructor that sets the service to lazy generate if you don't give the thread pool an action to run immediately.
     */
    public ExecutorManager(Supplier<ExecutorService> deferredExecutorService) {
        this.deferredExecutorService = deferredExecutorService;
    }

    /**
     * Submit tasks stored in the blockingRunnableQueue to the thread pool.
     *
     * @throws NoSuchElementException - If there are no jobs in the blockingRunnableQueue
     */
    public void execute() {
        if (this.blockingRunnableQueue.isEmpty()) {
            throw new NoSuchElementException("No runnable tasks have been submitted.");
        }

        if (this.executorService == null) {
            getExecutorService();
        }

        blockingRunnableQueue.forEach(r -> executorService.submit(r));
    }

    /**
     * It performs the tasks stored in the blockingCallableQueue and returns the results to a list of {@link Future}.
     * <p>
     * {@link Future#get()} needs to be done to get the actual return value, and if the operation takes a long time, thread blocking can occur to get the result.
     *
     * @throws NoSuchElementException - If there are no jobs in the blockingCallableQueue
     */
    public List<Future<?>> executeAndReturn() {
        if (this.blockingCallableQueue.isEmpty()) {
            throw new NoSuchElementException("No callable tasks have been submitted.");
        }

        if (this.executorService == null) {
            getExecutorService();
        }

        return blockingCallableQueue.stream().map(c -> executorService.submit(c)).collect(Collectors.toList());
    }


    /**
     * Submit the Runnable to be executed immediately. It's the same as using a simple thread pool.
     */
    public void submit(Runnable runnable) {
        if (this.executorService == null) {
            getExecutorService();
        }
        this.executorService.submit(runnable);
    }

    /**
     * Submit the Callable to be executed immediately. It's the same as using a simple thread pool.
     * <p>
     * {@link Future#get()} needs to be done to get the actual return value, and if the operation takes a long time, thread blocking can occur to get the result.
     */
    public Future<?> submit(Callable<?> callable) {
        if (this.executorService == null) {
            getExecutorService();
        }
        return this.executorService.submit(callable);
    }

    public void deferredSubmit(Runnable runnable) {
        blockingRunnableQueue.add(runnable);
    }

    public void deferredSubmit(List<Runnable> runnableList) {
        blockingRunnableQueue.addAll(runnableList);
    }

    public void deferredSubmitCallable(Callable<?> callable) {
        blockingCallableQueue.add(callable);
    }

    public void deferredSubmitCallable(List<Callable<?>> callableList) {
        blockingCallableQueue.addAll(callableList);
    }

    private void getExecutorService() {
        this.executorService = deferredExecutorService.get();
    }
}
