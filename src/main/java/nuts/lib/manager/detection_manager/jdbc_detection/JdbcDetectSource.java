package nuts.lib.manager.detection_manager.jdbc_detection;

import nuts.lib.manager.detection_manager.DetectSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class JdbcDetectSource implements DetectSource<Map<String, Object>> {

    JdbcTemplate jdbcTemplate;
    String selectQuery;

    public JdbcDetectSource(String selectQuery) {
        this.selectQuery = selectQuery;
    }

    public static JdbcDetectSource jdbcDetectSourceTableAllSearch(String tableName) {
        return new JdbcDetectSource("select * from %s".formatted(tableName));
    }

    void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Map<String, Object>> poll() {
        return jdbcTemplate.queryForList(selectQuery);
    }
}
