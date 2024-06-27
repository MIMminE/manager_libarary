package nuts.lib.manager.executor_manager.async;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


class AsyncChainExecutorTest {

    @Test
    void cf_test() {
        TesterCF<Object> objectTesterCF = new TesterCF<>();

        System.out.println(objectTesterCF.isCancelled() + " " + objectTesterCF.getWorkStatus());
        System.out.println(objectTesterCF.isDone());

    }

    @Test
    void test() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 테스트중");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 테스트중");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Map<String, Runnable> stringRunnableMap = Map.of("1", runnable, "2", runnable2);
        AsyncChainExecutor asyncChainExecutor = new AsyncChainExecutor(List.of("1", "2"));


        asyncChainExecutor.sync();


        asyncChainExecutor.test("1", runnable);

        Thread.sleep(10000);

        asyncChainExecutor.test("1", runnable);

        asyncChainExecutor.test("2", runnable2);

        Thread.sleep(100000);
    }

}