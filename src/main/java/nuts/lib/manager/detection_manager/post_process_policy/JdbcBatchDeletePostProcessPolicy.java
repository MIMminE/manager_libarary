package nuts.lib.manager.detection_manager.post_process_policy;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JdbcBatchDeletePostProcessPolicy implements JdbcPostProcessPolicy {
    private int batchSize = 200;
    private final String tableName;
    private final String keyField;
    private final JdbcTemplate jdbcTemplate;

    public JdbcBatchDeletePostProcessPolicy(String tableName, String keyField, JdbcTemplate jdbcTemplate) {
        this.tableName = tableName;
        this.keyField = keyField;
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcBatchDeletePostProcessPolicy(String tableName, String keyField, JdbcTemplate jdbcTemplate, int batchSize) {
        this.tableName = tableName;
        this.keyField = keyField;
        this.jdbcTemplate = jdbcTemplate;
        this.batchSize = batchSize;
    }

    @Override
    public void process(List<Map<String, Object>> processingTarget) {

        List<List<Map<String, Object>>> batches = splitList(processingTarget, batchSize);
        batches.forEach(this::batchDelete);
    }

    private <T> List<List<T>> splitList(List<T> list, int batchSize) {
        List<List<T>> result = new ArrayList<>();
        int totalSize = list.size();

        for (int i = 0; i < totalSize; i += batchSize) {
            int end = Math.min(totalSize, i + batchSize);
            result.add(new ArrayList<>(list.subList(i, end)));
        }

        return result;
    }

    private void batchDelete(List<Map<String, Object>> sources) {

        jdbcTemplate.batchUpdate(sources.stream()
                .map(source -> "Delete from %s where %s = %s".formatted(tableName, keyField, source.get(keyField))).toArray(String[]::new));

    }

}

