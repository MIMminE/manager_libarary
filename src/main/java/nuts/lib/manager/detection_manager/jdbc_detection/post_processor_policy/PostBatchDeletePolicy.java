package nuts.lib.manager.detection_manager.jdbc_detection.post_processor_policy;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostBatchDeletePolicy extends PostProcessorPolicy {

    private int batchSize;
    private String tableName;
    private String keyField;

    @Override
    public void delegate(List<Map<String, Object>> targetList) {
        splitList(targetList, batchSize).forEach(this::batchDelete);
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

    static public PostBatchDeletePolicy builder(String tableName, String keyField) {
        return new PostBatchDeletePolicy(200, tableName, keyField);
    }

    static public PostBatchDeletePolicy builder(String tableName, String keyField, int batchSize) {
        return new PostBatchDeletePolicy(batchSize, tableName, keyField);
    }

}
