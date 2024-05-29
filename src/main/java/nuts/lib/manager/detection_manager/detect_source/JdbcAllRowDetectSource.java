package nuts.lib.manager.detection_manager.detect_source;

import nuts.lib.manager.detection_manager.post_process_policy.JdbcPostProcessPolicy;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class JdbcAllRowDetectSource implements JdbcDetectSource {

    private final JdbcTemplate jdbcTemplate;
    private final String tableName;
    private final JdbcPostProcessPolicy postProcessPolicy;

    public JdbcAllRowDetectSource(JdbcTemplate jdbcTemplate, String tableName, JdbcPostProcessPolicy postProcessPolicy) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = tableName;
        this.postProcessPolicy = postProcessPolicy;
    }

    @Override
    public List<Map<String, Object>> poll() {
        return jdbcTemplate.queryForList("select * from %s".formatted(tableName));
    }

    @Override
    public void postProcess(List<Map<String, Object>> target) {
        postProcessPolicy.process(target);
    }
}
