package nuts.lib.manager.detection_manager;

import java.util.List;

public interface PostProcessor<T> {

    void process(List<T> TargetList);
}
