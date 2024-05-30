package nuts.lib.manager.detection_manager.jdbc_detection.post_processor_policy;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostBatchUpdatePolicy extends PostProcessorPolicy {

    private int batchSize;
    private String tableName;
    private String keyField;
    private String updateField;
    private String updateValue;

    @Override
    public void delegate(List<Map<String, Object>> targetList) {
        splitList(targetList, batchSize).forEach(this::batchUpdate);
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
                .map(source -> "Update %s Set %s = '%s' where %s = %s".formatted(tableName, updateField, updateValue, keyField, source.get(keyField))).toArray(String[]::new));

    }

    static public PostBatchUpdatePolicy builder(String tableName, String keyField, String updateField, String updateValue) {
        return new PostBatchUpdatePolicy(200, tableName, keyField, updateField, updateValue);
    }

    static public PostBatchUpdatePolicy builder(String tableName, String keyField, String updateField, String updateValue, int batchSize) {
        return new PostBatchUpdatePolicy(batchSize, tableName, keyField, updateField, updateValue);
    }

}
