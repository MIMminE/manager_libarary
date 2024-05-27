package nuts.lib.manager.detection_manager.detectors;

import nuts.lib.manager.detection_manager.DetectSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class SimpleJdbcDetectSource implements DetectSource<Map<String, Object>> {

    private final JdbcTemplate jdbcTemplate;
    private final String tableName;
    private List<Map<String, Object>> currentSelectPoint;

    public SimpleJdbcDetectSource(DataSource dataSource, String tableName) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.tableName = tableName;
    }

    @Override
    public List<Map<String, Object>> poll() {
        currentSelectPoint = jdbcTemplate.queryForList("select * from %s".formatted(tableName));
        return currentSelectPoint;
    }

    @Override
    public void postProcess(List<Map<String, Object>> target) {
        for (Map<String, Object> row : target) {
            this.jdbcTemplate.update("delete from %s where id = ?".formatted(tableName), row.get("id"));
        }
    }

}
