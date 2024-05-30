package nuts.lib.manager.detection_manager.jdbc_detection.builder;

import nuts.lib.manager.detection_manager.jdbc_detection.post_processor_policy.DefaultPostProcessorPolicy;
import nuts.lib.manager.detection_manager.jdbc_detection.post_processor_policy.PostBatchDeletePolicy;
import nuts.lib.manager.detection_manager.jdbc_detection.post_processor_policy.PostBatchUpdatePolicy;
import nuts.lib.manager.detection_manager.jdbc_detection.post_processor_policy.PostProcessorPolicy;

public class JdbcPostProcessorConfigurer {

    PostProcessorPolicy postProcessorPolicy;

    public JdbcPostProcessorConfigurer deleteBatch(String tableName, String keyField) {
        this.postProcessorPolicy = PostBatchDeletePolicy.builder(tableName, keyField);
        return this;
    }

    public JdbcPostProcessorConfigurer deleteBatch(String tableName, String keyField, int batchSize) {
        this.postProcessorPolicy = PostBatchDeletePolicy.builder(tableName, keyField, batchSize);
        return this;
    }

    public JdbcPostProcessorConfigurer updateBatch(String tableName, String keyField, String updateField, String updateValue) {
        this.postProcessorPolicy = PostBatchUpdatePolicy.builder(tableName, keyField, updateField, updateValue);
        return this;
    }

    public JdbcPostProcessorConfigurer updateBatch(String tableName, String keyField, String updateField, String updateValue, int batchSize) {
        this.postProcessorPolicy = PostBatchUpdatePolicy.builder(tableName, keyField, updateField, updateValue, batchSize);
        return this;
    }

    public JdbcPostProcessorConfigurer customizer(PostProcessorPolicy postProcessorPolicy){
        this.postProcessorPolicy = postProcessorPolicy;
        return this;
    }

    public JdbcPostProcessorConfigurer defaultMode() {
        this.postProcessorPolicy = DefaultPostProcessorPolicy.PRINT_POLICY;
        return this;
    }

}
