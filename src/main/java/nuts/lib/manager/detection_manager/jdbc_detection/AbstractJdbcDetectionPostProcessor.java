package nuts.lib.manager.detection_manager.jdbc_detection;

import nuts.lib.manager.detection_manager.DetectionPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public abstract class AbstractJdbcDetectionPostProcessor implements DetectionPostProcessor<Map<String, Object>> {

    private JdbcTemplate jdbcTemplate;

    @Override
    public void process(List<Map<String, Object>> detectedObjects) {

        jdbcTemplate.batchUpdate(getPostProcessingQuery(detectedObjects));
    }

    /**
     * The return of the getDetectionQuery method should return the SQL Select query string,
     * and the query action determines that it has detected the retrieved data.
     *
     * @since 2024. 06. 19
     */
    protected abstract String[] getPostProcessingQuery(List<Map<String, Object>> detectedObjects);
}
