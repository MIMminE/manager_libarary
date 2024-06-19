package nuts.lib.manager.detection_manager;

import java.util.List;

public interface DetectionHandler<T> {
    void process(List<T> detectedObjects);
}
