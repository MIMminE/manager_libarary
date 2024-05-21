package nuts.lib.manager.executor_manager;

import nuts.lib.manager.executor_manager.executor.ExecutorBuilder;
import org.springframework.security.core.parameters.P;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

public class ExecutorManager {

    private ExecutorService executorService;
    private ScheduledExecutorService scheduledExecutorService;
    private boolean executeSignal = true;

    public ExecutorManager() {
        this.executorService = ExecutorBuilder.newFixedExecutor(5);
        this.scheduledExecutorService = ExecutorBuilder.newScheduledExecutor(1);
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
        this.scheduledExecutorService = null;
    }

    public void runnableSubmit(Runnable r) {
        executorService.submit(r);
    }

    public void conditionSubmit(Runnable r) throws InterruptedException {
        if (executeSignal) {
            synchronized (this) {
                this.executeSignal = false;
            }
        }

        System.out.println(r);
        executorService.submit(

                () -> {
                    while (!executeSignal) {
                        System.out.println("wait!" + Thread.currentThread().getName());
                        try {
                            Thread.sleep(2000);
                            synchronized (this) {
                                this.wait();
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    r.run();

                }
        );

    }

    public void sendExecuteSignal() {
        if (executeSignal) throw new RuntimeException("this manager signal is already true");

        synchronized (this) {
            this.executeSignal = true;
            this.notifyAll();
        }
    }
}
