package nuts.lib.manager.executor_manager;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

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
     * If you use Runnable as the first parameter, the ScheduledExecutorService you entered the constructor will be the thread pool that does the work.
     * <p>
     * and if you enter ExecutorManger, the [ExecutorService] you passed in to create the ExecutorManger will be the thread pool that does the work.
     *
     * @author nuts
     * @since 2024. 05. 22
     */
    public void schedule(Runnable runnable, long millisTerms, String scheduleName) {
        getScheduledExecutorService();

        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(runnable, 0L, millisTerms, TimeUnit.MILLISECONDS);
        scheduledFutureMap.put(scheduleName, scheduledFuture);
    }


    public void schedule(ExecutorManager executorManager, long millisTerms, String scheduleName) {
        getScheduledExecutorService();

        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(executorManager::execute, 0L, millisTerms, TimeUnit.MILLISECONDS);
        scheduledFutureMap.put(scheduleName, scheduledFuture);
    }


    /**
     * @param runnable
     * @param millisTerms
     * @param limitCount
     * @param scheduleName
     */
    public void schedule(Runnable runnable, long millisTerms, long limitCount, String scheduleName) {
        getScheduledExecutorService();
        scheduleLimitMap.put(scheduleName, new AtomicInteger(0));

        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(limitCountProxy(runnable, limitCount, scheduleName), 0L, millisTerms, TimeUnit.MILLISECONDS);
        scheduledFutureMap.put(scheduleName, scheduledFuture);
    }

    public void schedule(ExecutorManager executorManager, long millisTerms, long limitCount, String scheduleName) {
        getScheduledExecutorService();
        scheduleLimitMap.put(scheduleName, new AtomicInteger(0));

        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(limitCountProxy(executorManager, limitCount, scheduleName), 0L, millisTerms, TimeUnit.MILLISECONDS);
        scheduledFutureMap.put(scheduleName, scheduledFuture);
    }


    /**
     * @param runnable
     * @param specificTime
     * @param scheduleName
     */
    public void scheduleDailyAtSpecificTime(Runnable runnable, LocalTime specificTime, String scheduleName) {
        getScheduledExecutorService();

        long timeUntil = getTimeUntil(specificTime);
        scheduledExecutorService.scheduleAtFixedRate(runnable, timeUntil, 86400, TimeUnit.SECONDS);
    }

    public void scheduleDailyAtSpecificTime(ExecutorManager executorManager, LocalTime specificTime, String scheduleName) {
        getScheduledExecutorService();

        long timeUntil = getTimeUntil(specificTime);
        scheduledExecutorService.scheduleAtFixedRate(executorManager::execute, timeUntil, 86400, TimeUnit.SECONDS);
    }


    /**
     * @param runnable
     * @param specificTime
     * @param limitCount
     * @param scheduleName
     */
    public void scheduleDailyAtSpecificTime(Runnable runnable, LocalTime specificTime, long limitCount, String scheduleName) {
        getScheduledExecutorService();
        scheduleLimitMap.put(scheduleName, new AtomicInteger(0));

        long timeUntil = getTimeUntil(specificTime);
        scheduledExecutorService.scheduleAtFixedRate(limitCountProxy(runnable, limitCount, scheduleName), timeUntil, 86400, TimeUnit.SECONDS);
    }

    public void scheduleDailyAtSpecificTime(ExecutorManager executorManager, LocalTime specificTime, long limitCount, String scheduleName) {
        getScheduledExecutorService();
        scheduleLimitMap.put(scheduleName, new AtomicInteger(0));

        long timeUntil = getTimeUntil(specificTime);
        scheduledExecutorService.scheduleAtFixedRate(limitCountProxy(executorManager, limitCount, scheduleName), timeUntil, 86400, TimeUnit.SECONDS);
    }


    /**
     * @param runnable
     * @param specificTime
     * @param secondTerm
     * @param scheduleName
     */
    public void scheduleAtSpecificTime(Runnable runnable, LocalTime specificTime, long secondTerm, String scheduleName) {
        getScheduledExecutorService();
        scheduleLimitMap.put(scheduleName, new AtomicInteger(0));

        long timeUntil = getTimeUntil(specificTime);
        scheduledExecutorService.scheduleAtFixedRate(runnable, timeUntil, secondTerm, TimeUnit.SECONDS);
    }

    public void scheduleAtSpecificTime(ExecutorManager executorManager, LocalTime specificTime, long secondTerm, String scheduleName) {
        getScheduledExecutorService();
        scheduleLimitMap.put(scheduleName, new AtomicInteger(0));

        long timeUntil = getTimeUntil(specificTime);
        scheduledExecutorService.scheduleAtFixedRate(executorManager::execute, timeUntil, secondTerm, TimeUnit.SECONDS);
    }


    /**
     * @param runnable
     * @param specificTime
     * @param secondTerm
     * @param limitCount
     * @param scheduleName
     */

    public void scheduleAtSpecificTime(Runnable runnable, LocalTime specificTime, long secondTerm, long limitCount, String scheduleName) {
        getScheduledExecutorService();
        scheduleLimitMap.put(scheduleName, new AtomicInteger(0));

        long timeUntil = getTimeUntil(specificTime);
        scheduledExecutorService.scheduleAtFixedRate(limitCountProxy(runnable, limitCount, scheduleName), timeUntil, secondTerm, TimeUnit.SECONDS);
    }

    public void scheduleAtSpecificTime(ExecutorManager executorManager, LocalTime specificTime, long secondTerm, long limitCount, String scheduleName) {
        getScheduledExecutorService();
        scheduleLimitMap.put(scheduleName, new AtomicInteger(0));

        long timeUntil = getTimeUntil(specificTime);
        scheduledExecutorService.scheduleAtFixedRate(limitCountProxy(executorManager, limitCount, scheduleName), timeUntil, secondTerm, TimeUnit.SECONDS);
    }


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
