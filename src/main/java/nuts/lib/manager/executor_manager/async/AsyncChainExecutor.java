package nuts.lib.manager.executor_manager.async;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
public class AsyncChainExecutor {

    @Getter
    final Map<String, CompletableFuture<Integer>> completableFutureMap = new HashMap<>();
    private boolean init;

    public AsyncChainExecutor(List<String> keyList) {
        for (String s : keyList) {
            completableFutureMap.put(s, new CompletableFuture<>());
        }
        init = true;
    }

    public void test(String key, Runnable runnable) {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        completableFutureMap.put(key, completableFuture);
        CompletableFuture.runAsync(runnable).thenRun(() -> completableFuture.complete(0));
    }

    public void sync() throws InterruptedException {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                dynamicPrint();
            }

        }).start();
    }

    private void dynamicPrint() {
        String s = "hello";
        if (!completableFutureMap.get("1").isDone()) s += " 0번 추가";
        if (!completableFutureMap.get("2").isDone()) s += " 1번 추가";
        System.out.println(s);

    }
}
