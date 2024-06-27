package nuts.lib.manager.executor_manager.async;

import lombok.Getter;

import java.util.concurrent.CompletableFuture;

@Getter
public class TesterCF<T> extends CompletableFuture<T> {

    private WorkStatus workStatus;

    public TesterCF() {
        this.workStatus = WorkStatus.init;
    }

    public void updateWorking() {
        this.workStatus = WorkStatus.working;
    }

    public void updateDone() {
        this.workStatus = WorkStatus.done;
    }

    enum WorkStatus {
        init,
        working,
        done
    }
}
