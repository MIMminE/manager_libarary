package nuts.lib.manager.detection_manager.jdbc_detection;

import nuts.lib.manager.detection_manager.DetectionProcessor;
import nuts.lib.manager.detection_manager.jdbc_detection.detection_processor_policy.DetectionProcessorPolicy;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class JdbcDetectionProcessor implements DetectionProcessor<Map<String, Object>> {

    private final DetectionProcessorPolicy policy;

    public JdbcDetectionProcessor(DetectionProcessorPolicy policy) {
        this.policy = policy;
    }

    void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.policy.setJdbcTemplate(jdbcTemplate);
    }

    @Override
    public List<Map<String, Object>> process(List<Map<String, Object>> targetList) {

        return policy.delegate(targetList);
    }
}


