package nuts.lib.manager.detection_manager.jdbc_detection;

import nuts.lib.manager.detection_manager.DetectionHandler;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

public abstract class AbstractJdbcDetectionHandler implements DetectionHandler<Map<String, Object>> {

    private JdbcTemplate jdbcTemplate;
}
