package nuts.lib.manager.detection_manager.jdbc_detection.builder;

import lombok.Getter;
import nuts.lib.manager.detection_manager.jdbc_detection.detection_processor_policy.DefaultDetectionProcessorPolicy;
import nuts.lib.manager.detection_manager.jdbc_detection.detection_processor_policy.DetectionProcessorPolicy;

public class JdbcDetectionProcessorConfigurer {

    DetectionProcessorPolicy detectionProcessorPolicy;

    public JdbcDetectionProcessorConfigurer customize(DetectionProcessorPolicy detectionProcessorPolicy) {
        this.detectionProcessorPolicy = detectionProcessorPolicy;
        return this;
    }

    public JdbcDetectionProcessorConfigurer defaultMode(){
        this.detectionProcessorPolicy = DefaultDetectionProcessorPolicy.PRINT_POLICY;
        return this;
    }
}
