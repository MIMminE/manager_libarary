package nuts.lib.manager.detection_manager.jdbc_detection.detection_processor_policy;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class DetectionPrintPolicy extends DetectionProcessorPolicy {
    @Override
    public List<Map<String, Object>> delegate(List<Map<String, Object>> targetList) {

        for (Map<String, Object> target : targetList) {
            log.info("detect {}", target);
        }
        return targetList;
    }
}
