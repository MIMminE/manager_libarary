package nuts.lib.manager.executor_manager;

import nuts.lib.manager.executor_manager.executor.ExecutorBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ConditionTaskScheduler extends abstractConditionScheduler<List<Runnable>> {

    private ExecutorService executorService = ExecutorBuilder.newFixedExecutor(5);


    public void addTask(Runnable runnable) {
        taskSupplier.add(runnable);
    }

    public void run() {
        for (Runnable runnable : taskSupplier) {
            executorService.submit(proxyRunnable(runnable));
        }
    }

    public void updateCondition() {
        synchronized (this) {
            this.condition = true;
            synchronized (this) {
                this.notifyAll();
            }
        }
    }

    private Runnable proxyRunnable(Runnable runnable) {
        return () -> {

            synchronized (this) {
                while (!condition) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            runnable.run();
        };
    }


    @Override
    protected List<Runnable> initTaskSupplier() {
        return new ArrayList<>();
    }
}
