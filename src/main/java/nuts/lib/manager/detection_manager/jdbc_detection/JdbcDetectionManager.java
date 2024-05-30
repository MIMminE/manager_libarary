package nuts.lib.manager.detection_manager.jdbc_detection;

import nuts.lib.manager.executor_manager.ScheduleExecutorManager;
import nuts.lib.manager.executor_manager.executor.ExecutorBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class JdbcDetectionManager {
    private final ScheduleExecutorManager scheduleExecutorManager = new ScheduleExecutorManager(ExecutorBuilder.newSingleThreadScheduledExecutor("detect_manager"));
    private final JdbcDetectSource detectSource;
    private final JdbcDetectionProcessor detectionProcessor;
    private final JdbcPostProcessor postProcessor;
    private final long intervalMillis;

    public JdbcDetectionManager(JdbcTemplate jdbcTemplate, JdbcDetectSource detectSource, JdbcDetectionProcessor detectionProcessor, JdbcPostProcessor postProcessor, long intervalMillis) {
        this.detectSource = detectSource;
        this.postProcessor = postProcessor;
        this.detectionProcessor = detectionProcessor;
        this.intervalMillis = intervalMillis;
        this.detectSource.setJdbcTemplate(jdbcTemplate);
        this.detectionProcessor.setJdbcTemplate(jdbcTemplate);
        this.postProcessor.setJdbcTemplate(jdbcTemplate);
    }

    @Transactional
    public void run() throws ExecutionException, InterruptedException {
        scheduleExecutorManager.schedule(() -> {
            List<Map<String, Object>> processed = detectionProcessor.process(detectSource.poll());
            postProcessor.process(processed);
        }, intervalMillis, "jdbc monitoring");
    }
}
