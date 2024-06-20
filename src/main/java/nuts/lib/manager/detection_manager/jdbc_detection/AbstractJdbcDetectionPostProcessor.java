package nuts.lib.manager.detection_manager.jdbc_detection;

import nuts.lib.manager.detection_manager.DetectionPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public abstract class AbstractJdbcDetectionPostProcessor implements DetectionPostProcessor<Map<String, Object>> {

    private final JdbcTemplate jdbcTemplate;

    public AbstractJdbcDetectionPostProcessor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void process(List<Map<String, Object>> detectedObjects) {

        jdbcTemplate.batchUpdate(getPostProcessingQuery(detectedObjects));
    }

    /**
     * Define the subsequent processing of detected and processed data.
     * As a return value, you need to return an array of SQL strings for subsequent processing,
     * and this is done in batches.
     *
     * @since 2024. 06. 19
     */
    protected abstract String[] getPostProcessingQuery(List<Map<String, Object>> detectedObjects);
}
