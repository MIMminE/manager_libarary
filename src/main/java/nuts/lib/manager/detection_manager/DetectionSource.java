package nuts.lib.manager.detection_manager;

import java.util.List;

public interface DetectionSource<T> {

    List<T> poll();

}
