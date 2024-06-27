package nuts.lib.manager.detection_manager.jdbc_detection;

import lombok.extern.slf4j.Slf4j;
import nuts.lib.manager.executor_manager.ScheduleExecutorManager;
import nuts.lib.manager.executor_manager.executor.ExecutorBuilder;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Change Detection Manager using JdbcTemplate.
 * <p>
 * Set up a scheduler program based on three abstract class implementations that are implemented by writing SQL directly.
 * <p>
 * - {@link AbstractJdbcDetectionSource} : Use SQL select syntax to retrieve data that meets the criteria in the table. The data viewed is judged to have been 'detected'. <p>
 * - {@link AbstractJdbcDetectionHandler} : Implement a handling function for how to process the retrieved data. <p>
 * - {@link AbstractJdbcDetectionPostProcessor} : Define the subsequent processing of the detected data. It can be applied mainly by changing or deleting the value of a particular flag in the data.<p>
 * Each manager runs on a separate schedule thread pool, and the thread pool can be customized via a constructor.
 *
 * @since 2024. 06. 20
 */

@Slf4j
public class JdbcDetectionManager {
    protected final AbstractJdbcDetectionSource detectionSource;
    protected final AbstractJdbcDetectionHandler detectionHandler;
    protected final AbstractJdbcDetectionPostProcessor detectionPostProcessor;
    protected final ScheduleExecutorManager scheduleExecutorManager;
    protected final TransactionTemplate transactionTemplate;
    protected final long intervalMills;
    protected final String scheduleName;

    public JdbcDetectionManager(AbstractJdbcDetectionSource detectionSource, AbstractJdbcDetectionHandler detectionHandler,
                                AbstractJdbcDetectionPostProcessor detectionPostProcessor, TransactionTemplate transactionTemplate, long intervalMills) {
        this.detectionSource = detectionSource;
        detectionHandler.initPostProcessor(detectionPostProcessor);
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
        detectionHandler.initPostProcessor(detectionPostProcessor);
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
        detectionHandler.initPostProcessor(detectionPostProcessor);
        this.detectionHandler = detectionHandler;
        this.detectionPostProcessor = detectionPostProcessor;
        this.scheduleExecutorManager = scheduleExecutorManager;
        this.transactionTemplate = transactionTemplate;
        this.intervalMills = intervalMills;
        this.scheduleName = scheduleName;
    }

    /**
     * You can initiate the manager's action through the method.
     * <p>
     * It refers to the operation of the scheduler that is inside of it, and it works asynchronously.
     *
     * @since 2024. 06. 20
     */
    public void run() throws ExecutionException, InterruptedException {
        scheduleExecutorManager.schedule(() -> {
            transactionTemplate.execute(status -> {
                List<Map<String, Object>> poll = detectionSource.poll();
                if (!poll.isEmpty()) {
                    log.debug("detect -> {}", poll);
                    detectionHandler.process(poll);
                }

                return null;
            });
        }, intervalMills, scheduleName);
    }


}
