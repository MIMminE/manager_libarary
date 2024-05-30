package nuts.lib.manager.detection_manager.jdbc_detection;

import nuts.lib.manager.detection_manager.PostProcessor;
import nuts.lib.manager.detection_manager.jdbc_detection.post_processor_policy.PostProcessorPolicy;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class JdbcPostProcessor implements PostProcessor<Map<String, Object>> {

    private final PostProcessorPolicy policy;


    public JdbcPostProcessor(PostProcessorPolicy policy) {
        this.policy = policy;
    }

    void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.policy.setJdbcTemplate(jdbcTemplate);
    }

    @Override
    public void process(List<Map<String, Object>> TargetList) {
        policy.delegate(TargetList);
    }
}
