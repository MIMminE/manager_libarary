package nuts.lib.manager.detection_manager.jdbc_detection;

import nuts.lib.manager.executor_manager.ScheduleExecutorManager;
import nuts.lib.manager.executor_manager.executor.ExecutorBuilder;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class JdbcDetectionManager {
    private final AbstractJdbcDetectionSource detectionSource;
    private final AbstractJdbcDetectionHandler detectionHandler;
    private final AbstractJdbcDetectionPostProcessor detectionPostProcessor;
    private final ScheduleExecutorManager scheduleExecutorManager;
    private final TransactionTemplate transactionTemplate;
    private final long intervalMills;
    private final String scheduleName;

    public JdbcDetectionManager(AbstractJdbcDetectionSource detectionSource, AbstractJdbcDetectionHandler detectionHandler,
                                AbstractJdbcDetectionPostProcessor detectionPostProcessor, TransactionTemplate transactionTemplate, long intervalMills) {
        this.detectionSource = detectionSource;
        this.detectionHandler = detectionHandler;
        this.detectionPostProcessor = detectionPostProcessor;
        this.scheduleExecutorManager = new ScheduleExecutorManager(ExecutorBuilder.newSingleThreadScheduledExecutor("jdbc_detection_manager"));
        this.transactionTemplate = transactionTemplate;
        this.intervalMills = intervalMills;
        this.scheduleName = "none";
    }

    public JdbcDetectionManager(AbstractJdbcDetectionSource detectionSource, AbstractJdbcDetectionHandler detectionHandler,
                                AbstractJdbcDetectionPostProcessor detectionPostProcessor, ScheduleExecutorManager scheduleExecutorManager,
                                TransactionTemplate transactionTemplate, long intervalMills) {
        this.detectionSource = detectionSource;
        this.detectionHandler = detectionHandler;
        this.detectionPostProcessor = detectionPostProcessor;
        this.scheduleExecutorManager = scheduleExecutorManager;
        this.transactionTemplate = transactionTemplate;
        this.intervalMills = intervalMills;
        this.scheduleName = "none";
    }

    public JdbcDetectionManager(AbstractJdbcDetectionSource detectionSource, AbstractJdbcDetectionHandler detectionHandler,
                                AbstractJdbcDetectionPostProcessor detectionPostProcessor, ScheduleExecutorManager scheduleExecutorManager,
                                TransactionTemplate transactionTemplate, long intervalMills, String scheduleName) {
        this.detectionSource = detectionSource;
        this.detectionHandler = detectionHandler;
        this.detectionPostProcessor = detectionPostProcessor;
        this.scheduleExecutorManager = scheduleExecutorManager;
        this.transactionTemplate = transactionTemplate;
        this.intervalMills = intervalMills;
        this.scheduleName = scheduleName;
    }

    public void run() throws ExecutionException, InterruptedException {
        scheduleExecutorManager.schedule(() -> {
            transactionTemplate.execute(status -> {

                List<Map<String, Object>> poll = detectionSource.poll();

                detectionHandler.process(poll);

                detectionPostProcessor.process(poll);

                return null;
            });
        }, intervalMills, scheduleName);
    }
}
