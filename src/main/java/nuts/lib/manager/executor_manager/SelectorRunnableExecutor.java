package nuts.lib.manager.executor_manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

public class SelectorRunnableExecutor {

    private ExecutorService executorService;
    private final Map<Runnable, Boolean> singalMap = new ConcurrentHashMap<>();

    public SelectorRunnableExecutor(ExecutorService executorService) {
        this.executorService = executorService;
    }


    public void submit(Runnable runnable) {
        executorService.submit(proxyRunnable(runnable));
    }


    public void sendSignal(Runnable runnable) {
        if (singalMap.get(runnable) != null && !singalMap.get(runnable)) {
            synchronized (this) {
                singalMap.put(runnable, true);
                this.notifyAll();
                System.out.println(runnable + " is notify");
            }
        }
    }

    private Runnable proxyRunnable(Runnable runnable) {
        return () -> {
            singalMap.put(runnable, false);
            synchronized (this) {
                try {
                    System.out.println(runnable + " is locked");
                    while (!singalMap.get(runnable)) {
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            runnable.run();
        };
    }
}
