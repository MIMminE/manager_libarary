package nuts.lib.manager.executor_manager;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
     *
     * @param deferredExecutorService a supplier that provides the ExecutorService lazily
     * @author nuts
     * @since 2024. 05. 22
     */
    public ExecutorManager(Supplier<ExecutorService> deferredExecutorService) {
        this.deferredExecutorService = deferredExecutorService;
    }

    /**
     * Example - execute()
     * <pre>
     * {@code
     *         Supplier<ExecutorService> serviceSupplier = () -> ExecutorBuilder.newFixedExecutor(5);
     *
     *         ExecutorManager executorManager = new ExecutorManager(serviceSupplier);
     *
     *         executorManager.deferredSubmit(() -> System.out.println("first Runnable"));
     *         executorManager.deferredSubmit(() -> System.out.println("second Runnable"));
     *         executorManager.deferredSubmit(() -> System.out.println("third Runnable"));
     *
     *         executorManager.execute();
     *
     * }
     * </pre>
     *
     * @author nuts
     * @since 2024. 05. 22
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
     * Example - executeAndReturn()
     *
     * <pre>
     * {@code
     *         Supplier<ExecutorService> serviceSupplier = () -> ExecutorBuilder.newFixedExecutor(5);
     *
     *         ExecutorManager executorManager = new ExecutorManager(serviceSupplier);
     *
     *         executorManager.deferredSubmitCallable(() -> "first Callable");
     *         executorManager.deferredSubmitCallable(() -> "second Callable");
     *         executorManager.deferredSubmitCallable(() -> "third Callable");
     *
     *         List<Future<?>> futures = executorManager.executeAndReturn();
     *
     * }
     * </pre>
     * @author nuts
     * @since 2024. 05. 22
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

    public void submit(Runnable runnable) {
        if (this.executorService == null) {
            getExecutorService();
        }
        this.executorService.submit(runnable);
    }

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
