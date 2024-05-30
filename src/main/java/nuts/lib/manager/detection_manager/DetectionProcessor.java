package nuts.lib.manager.detection_manager;

import java.util.List;

public interface DetectionProcessor<T>{
    List<T> process(List<T> targetList);
}
