package nuts.lib.manager.detection_manager.jdbc_detection.post_processor_policy;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class PostPrintPolicy extends PostProcessorPolicy {

    @Override
    public void delegate(List<Map<String, Object>> targetList) {
        for (Map<String, Object> target : targetList) {
            log.info("post disposal {}", target);
        }
    }
}
