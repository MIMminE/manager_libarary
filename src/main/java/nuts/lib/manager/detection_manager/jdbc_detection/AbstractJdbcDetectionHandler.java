package nuts.lib.manager.detection_manager.jdbc_detection;

import nuts.lib.manager.detection_manager.DetectionHandler;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * It is an abstract class that defines how to handle detected data.
 * <p>
 * You can override the {@link DetectionHandler#process(List)} method to perform handling operations on the data queried by the DetectionSource.
 *
 * @since 2024. 06. 20
 */
public abstract class AbstractJdbcDetectionHandler implements DetectionHandler<Map<String, Object>> {

    public AbstractJdbcDetectionHandler(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final JdbcTemplate jdbcTemplate;
    private AbstractJdbcDetectionPostProcessor postProcessor;

    public void initPostProcessor(AbstractJdbcDetectionPostProcessor postProcessor) {
        this.postProcessor = postProcessor;
    }

    protected void runPostProcessor(List<Map<String, Object>> objectList) {
        postProcessor.process(objectList);
    }
}
