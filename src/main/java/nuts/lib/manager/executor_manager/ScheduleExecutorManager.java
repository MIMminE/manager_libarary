package nuts.lib.manager.executor_manager;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * It provides the API needed to schedule tasks defined as {@link Runnable} or {@link ExecutorManager}.
 */
public class ScheduleExecutorManager {

    private ScheduledExecutorService scheduledExecutorService;
    private Supplier<ScheduledExecutorService> deferredScheduledExecutorService;
    private final BlockingQueue<Runnable> blockingRunnableQueue = new LinkedBlockingQueue<>();
    private final List<ExecutorManager> executorManagerList = new ArrayList<>();
    private final Map<String, ScheduledFuture<?>> scheduledFutureMap = new ConcurrentHashMap<>();
    private final Map<String, AtomicInteger> scheduleLimitMap = new ConcurrentHashMap<>();

    public ScheduleExecutorManager(ScheduledExecutorService scheduledExecutorService) {
        this.scheduledExecutorService = scheduledExecutorService;
    }

    /**
     * A constructor that sets the service to lazy generate if you don't give the thread pool an action to run immediately.
     *
     * @param deferredScheduledExecutorService a supplier that provides the ScheduledExecutorService lazily
     * @author nuts
     * @since 2024. 05. 22
     */
    public ScheduleExecutorManager(Supplier<ScheduledExecutorService> deferredScheduledExecutorService) {
        this.deferredScheduledExecutorService = deferredScheduledExecutorService;
    }

    /**
     * Iterate over the actions defined in {@link Runnable} or {@link ExecutorManager} in milliseconds.
     * <p>
     * It runs asynchronously on the thread pool given by the constructor or ExecutorManager constructor.
     * <p>
     * In the example, it runs on {@link nuts.lib.manager.executor_manager.executor.ExecutorBuilder#newScheduledExecutor(int)}.
     * <pre>
     *  {@code
     *  ScheduleExecutorManager scheduleExecutorManager =
     *                           new ScheduleExecutorManager(ExecutorBuilder.newScheduledExecutor(1));
     *  scheduleExecutorManager.schedule(
     *         ()-> System.out.println("Repetitive tasks"),
     *         2000,
     *         "Print Task Schedule"
     *  );
     *  }
     *  </pre>
     *
     * @author nuts
     * @since 2024. 05. 22
     */
    public void schedule(Runnable runnable, long millisTerms, String scheduleName) {
        getScheduledExecutorService();

        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(runnable, 0L, millisTerms, TimeUnit.MILLISECONDS);
        scheduledFutureMap.put(scheduleName, scheduledFuture);
    }


    /**
     * In the example, it runs on {@link nuts.lib.manager.executor_manager.executor.ExecutorBuilder#newFixedExecutor(int)}.
     * <p>
     * <pre>
     * {@code
     * ScheduleExecutorManager scheduleExecutorManager =
     *                  new ScheduleExecutorManager(ExecutorBuilder.newScheduledExecutor(1));
     * ExecutorManager executorManager = new ExecutorManager(ExecutorBuilder.newFixedExecutor(3));
     *
     * executorManager.deferredSubmit(List.of(
     *         () -> System.out.println("Repetitive tasks1"),
     *         () -> System.out.println("Repetitive tasks2")
     * ));
     * scheduleExecutorManager.schedule(executorManager, 2000, "Print Task Schedule");
     * }
     * </pre>
     *
     * @author nuts
     * @since 2024. 05. 23
     */
    public void schedule(ExecutorManager executorManager, long millisTerms, String scheduleName) {
        getScheduledExecutorService();

        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(executorManager::execute, 0L, millisTerms, TimeUnit.MILLISECONDS);
        scheduledFutureMap.put(scheduleName, scheduledFuture);
    }


    /**
     * Iterate on the action defined in the {@link Runnable} or {@link ExecutorManager} a set limitCount of times in a specific millisecond.
     * <p>
     * In the example, it runs on {@link nuts.lib.manager.executor_manager.executor.ExecutorBuilder#newScheduledExecutor(int)}.
     * <p>
     *
     * <pre>
     * {@code
     * ScheduleExecutorManager scheduleExecutorManager =
     *                  new ScheduleExecutorManager(ExecutorBuilder.newScheduledExecutor(1));
     *
     * scheduleExecutorManager.schedule(
     *       () -> System.out.println("Repetitive tasks"),
     *       2000,
     *       10,
     *       "Print Task Schedule"
     *  );
     * }
     * </pre>
     *
     * @author nuts
     * @since 2024. 05. 23
     */
    public void schedule(Runnable runnable, long millisTerms, long limitCount, String scheduleName) {
        getScheduledExecutorService();
        scheduleLimitMap.put(scheduleName, new AtomicInteger(0));

        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(limitCountProxy(runnable, limitCount, scheduleName), 0L, millisTerms, TimeUnit.MILLISECONDS);
        scheduledFutureMap.put(scheduleName, scheduledFuture);
    }

    /**
     * In the example, it runs on {@link nuts.lib.manager.executor_manager.executor.ExecutorBuilder#newFixedExecutor(int)}.
     * <p>
     * <pre>
     * {@code
     * ScheduleExecutorManager scheduleExecutorManager =
     *                  new ScheduleExecutorManager(ExecutorBuilder.newScheduledExecutor(1));
     * ExecutorManager executorManager = new ExecutorManager(ExecutorBuilder.newFixedExecutor(3));
     *
     * executorManager.deferredSubmit(List.of(
     *         () -> System.out.println("Repetitive tasks1"),
     *         () -> System.out.println("Repetitive tasks2")
     * ));
     * scheduleExecutorManager.schedule(executorManager, 2000, 10, "Print Task Schedule");
     * }
     * </pre>
     *
     * @author nuts
     * @since 2024. 05. 23
     */
    public void schedule(ExecutorManager executorManager, long millisTerms, long limitCount, String scheduleName) {
        getScheduledExecutorService();
        scheduleLimitMap.put(scheduleName, new AtomicInteger(0));

        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(limitCountProxy(executorManager, limitCount, scheduleName), 0L, millisTerms, TimeUnit.MILLISECONDS);
        scheduledFutureMap.put(scheduleName, scheduledFuture);
    }


    /**
     * It is scheduled to run daily at a time defined as {@link LocalTime}. It is set in seconds.
     * <p>
     * <pre>
     * {@code
     * scheduleExecutorManager.scheduleDailyAtSpecificTime(
     *              ()-> logService.deleteLog()), LocalTime.of(10, 24),"Delete Daily Log");
     * }
     * </pre>
     *
     * @author nuts
     * @since 2024. 05. 23
     */
    public void scheduleDailyAtSpecificTime(Runnable runnable, LocalTime iterationTime, String scheduleName) {
        getScheduledExecutorService();

        long timeUntil = getTimeUntil(iterationTime);
        scheduledExecutorService.scheduleAtFixedRate(runnable, timeUntil, 86400, TimeUnit.SECONDS);
    }

    /**
     * <pre>
     * {@code
     * scheduleExecutorManager.scheduleDailyAtSpecificTime(
     *              executorManager, LocalTime.of(10, 24),"Delete Daily Log");
     * }
     * </pre>
     */
    public void scheduleDailyAtSpecificTime(ExecutorManager executorManager, LocalTime iterationTime, String scheduleName) {
        getScheduledExecutorService();

        long timeUntil = getTimeUntil(iterationTime);
        scheduledExecutorService.scheduleAtFixedRate(executorManager::execute, timeUntil, 86400, TimeUnit.SECONDS);
    }


    /**
     * It is scheduled to run daily at a time defined as {@link LocalTime}. It is set in seconds. <p>
     * Set limitCount to limit the number of iterations (number of repeat days). <p>
     * <pre>
     * {@code
     * scheduleExecutorManager.scheduleDailyAtSpecificTime(
     *              ()-> logService.deleteLog()), LocalTime.of(10, 24), 3, "Delete Daily Log");
     * }
     * </pre>
     *
     * @author nuts
     * @since 2024. 05. 23
     */
    public void scheduleDailyAtSpecificTime(Runnable runnable, LocalTime iterationTime, long limitCount, String scheduleName) {
        getScheduledExecutorService();
        scheduleLimitMap.put(scheduleName, new AtomicInteger(0));

        long timeUntil = getTimeUntil(iterationTime);
        scheduledExecutorService.scheduleAtFixedRate(limitCountProxy(runnable, limitCount, scheduleName), timeUntil, 86400, TimeUnit.SECONDS);
    }

    /**
     * <pre>
     * {@code
     * scheduleExecutorManager.scheduleDailyAtSpecificTime(
     *              executorManager, LocalTime.of(10, 24), 3, "Delete Daily Log");
     * }
     * </pre>
     *
     * @author nuts
     * @since 2024. 05. 23
     */
    public void scheduleDailyAtSpecificTime(ExecutorManager executorManager, LocalTime iterationTime, long limitCount, String scheduleName) {
        getScheduledExecutorService();
        scheduleLimitMap.put(scheduleName, new AtomicInteger(0));

        long timeUntil = getTimeUntil(iterationTime);
        scheduledExecutorService.scheduleAtFixedRate(limitCountProxy(executorManager, limitCount, scheduleName), timeUntil, 86400, TimeUnit.SECONDS);
    }


    /**
     * Register a scheduler that repeats every unit of time from the start time. <p>
     * <pre>
     * {@code
     * scheduleExecutorManager.scheduleAtSpecificTime(
     *              ()-> logService.deleteLog()), LocalTime.of(10, 24), 1800, "Delete Daily Log");
     * }
     * </pre>
     *
     * @author nuts
     * @since 2024. 05. 23
     */
    public void scheduleAtSpecificTime(Runnable runnable, LocalTime startTime, long secondTerm, String scheduleName) {
        getScheduledExecutorService();
        scheduleLimitMap.put(scheduleName, new AtomicInteger(0));

        long timeUntil = getTimeUntil(startTime);
        scheduledExecutorService.scheduleAtFixedRate(runnable, timeUntil, secondTerm, TimeUnit.SECONDS);
    }

    /**
     * <pre>
     * {@code
     * scheduleExecutorManager.scheduleAtSpecificTime(
     *              executorManager, LocalTime.of(10, 24), 1800, "Delete Daily Log");
     * }
     * </pre>
     *
     * @author nuts
     * @since 2024. 05. 23
     */
    public void scheduleAtSpecificTime(ExecutorManager executorManager, LocalTime startTime, long secondTerm, String scheduleName) {
        getScheduledExecutorService();
        scheduleLimitMap.put(scheduleName, new AtomicInteger(0));

        long timeUntil = getTimeUntil(startTime);
        scheduledExecutorService.scheduleAtFixedRate(executorManager::execute, timeUntil, secondTerm, TimeUnit.SECONDS);
    }


    /**
     * Register a scheduler that iterates a certain number of times for every unit of time starting from the start time. <p>
     * <pre>
     * {@code
     * scheduleExecutorManager.scheduleAtSpecificTime(
     *              ()-> logService.deleteLog()), LocalTime.of(10, 24), 1800, 3, "Delete Daily Log");
     * }
     * </pre>
     *
     * @author nuts
     * @since 2024. 05. 23
     */
    public void scheduleAtSpecificTime(Runnable runnable, LocalTime startTime, long secondTerm, long limitCount, String scheduleName) {
        getScheduledExecutorService();
        scheduleLimitMap.put(scheduleName, new AtomicInteger(0));

        long timeUntil = getTimeUntil(startTime);
        scheduledExecutorService.scheduleAtFixedRate(limitCountProxy(runnable, limitCount, scheduleName), timeUntil, secondTerm, TimeUnit.SECONDS);
    }

    /**
     * <pre>
     * {@code
     * scheduleExecutorManager.scheduleAtSpecificTime(
     *              executorManager, LocalTime.of(10, 24), 1800, 3, "Delete Daily Log");
     * }
     * </pre>
     *
     * @author nuts
     * @since 2024. 05. 23
     */
    public void scheduleAtSpecificTime(ExecutorManager executorManager, LocalTime startTime, long secondTerm, long limitCount, String scheduleName) {
        getScheduledExecutorService();
        scheduleLimitMap.put(scheduleName, new AtomicInteger(0));

        long timeUntil = getTimeUntil(startTime);
        scheduledExecutorService.scheduleAtFixedRate(limitCountProxy(executorManager, limitCount, scheduleName), timeUntil, secondTerm, TimeUnit.SECONDS);
    }


    /**
     * Interrupt the running scheduler. Internally, we use the ScheduledFuture class API.
     *
     * @param scheduleName          name of the scheduler to abort
     * @param mayInterruptIfRunning Whether to force a quit for the currently running task, enter ture to abort the current task.
     * @author nuts
     * @since 2024. 05. 23
     */
    public void shutDownSchedule(String scheduleName, boolean mayInterruptIfRunning) {
        scheduledFutureMap.get(scheduleName).cancel(mayInterruptIfRunning);
    }

    private void getScheduledExecutorService() {
        if (this.scheduledExecutorService == null) {
            this.scheduledExecutorService = deferredScheduledExecutorService.get();
        }
    }

    private long getTimeUntil(LocalTime targetTime) {
        LocalTime currentTime = LocalTime.now();

        if (currentTime.isBefore(targetTime)) {
            return Duration.between(currentTime, targetTime).getSeconds();
        }
        return 86400 - Duration.between(targetTime, currentTime).getSeconds();
    }

    private Runnable limitCountProxy(Runnable runnable, long limitCount, String scheduleName) {
        return () -> {
            runnable.run();
            if (scheduleLimitMap.get(scheduleName).addAndGet(1) == limitCount)
                this.shutDownSchedule(scheduleName, false);
        };
    }

    private Runnable limitCountProxy(ExecutorManager executorManager, long limitCount, String scheduleName) {
        return () -> {
            executorManager.execute();
            if (scheduleLimitMap.get(scheduleName).addAndGet(1) == limitCount)
                this.shutDownSchedule(scheduleName, false);
        };
    }
}
