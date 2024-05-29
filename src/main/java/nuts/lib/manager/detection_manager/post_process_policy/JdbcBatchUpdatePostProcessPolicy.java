package nuts.lib.manager.detection_manager.post_process_policy;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JdbcBatchUpdatePostProcessPolicy implements JdbcPostProcessPolicy {

    private int batchSize = 200;
    private final String tableName;
    private final String keyField;
    private final String updateField;
    private final JdbcTemplate jdbcTemplate;

    public JdbcBatchUpdatePostProcessPolicy(String tableName, String keyField, String updateField, JdbcTemplate jdbcTemplate) {
        this.tableName = tableName;
        this.keyField = keyField;
        this.updateField = updateField;
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcBatchUpdatePostProcessPolicy(String tableName, String keyField, String updateField, JdbcTemplate jdbcTemplate, int batchSize) {
        this.tableName = tableName;
        this.keyField = keyField;
        this.updateField = updateField;
        this.jdbcTemplate = jdbcTemplate;
        this.batchSize = batchSize;
    }

    @Override
    public void process(List<Map<String, Object>> processingTarget) {

        List<List<Map<String, Object>>> batches = splitList(processingTarget, batchSize);
        batches.forEach(this::batchUpdate);
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

    private void batchUpdate(List<Map<String, Object>> sources) {

        jdbcTemplate.batchUpdate(sources.stream()
                .map(source -> "Update %s Set %s = '%s' where %s = %s".formatted(tableName, updateField, "done", keyField, source.get(keyField))).toArray(String[]::new));

    }
}

