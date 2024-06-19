package nuts.lib.manager.detection_manager;

import java.util.List;

public interface DetectionPostProcessor<T> {

    void process(List<T> detectedObjects);
}
