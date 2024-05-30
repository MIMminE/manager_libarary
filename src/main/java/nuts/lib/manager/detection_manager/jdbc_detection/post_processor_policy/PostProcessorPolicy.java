package nuts.lib.manager.detection_manager.jdbc_detection.post_processor_policy;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public abstract class PostProcessorPolicy {

    protected JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    abstract public void delegate(List<Map<String, Object>> targetList);
}
