package nuts.lib.manager.detection_manager.jdbc_detection.detection_processor_policy;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public abstract class DetectionProcessorPolicy {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    abstract public List<Map<String, Object>> delegate(List<Map<String, Object>> targetList);
}
