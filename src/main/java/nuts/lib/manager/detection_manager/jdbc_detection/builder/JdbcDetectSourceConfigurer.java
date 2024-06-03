package nuts.lib.manager.detection_manager.jdbc_detection.builder;

import lombok.Getter;
import nuts.lib.manager.detection_manager.jdbc_detection.JdbcDetectSource;

public class JdbcDetectSourceConfigurer {
    JdbcDetectSource jdbcDetectSource;

    public JdbcDetectSourceConfigurer selectQuery(String selectQuery) {
        this.jdbcDetectSource = new JdbcDetectSource(selectQuery);
        return this;
    }

    public JdbcDetectSourceConfigurer tableAllSearch(String tableName) {
        this.jdbcDetectSource = JdbcDetectSource.jdbcDetectSourceTableAllSearch(tableName);
        return this;
    }

    public JdbcDetectSourceConfigurer customizer(JdbcDetectSource jdbcDetectSource){
        this.jdbcDetectSource = jdbcDetectSource;
        return this;
    }
}
