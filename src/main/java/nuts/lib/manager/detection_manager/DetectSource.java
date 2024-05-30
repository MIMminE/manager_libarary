package nuts.lib.manager.detection_manager;

import java.util.List;

public interface DetectSource<T> {

    List<T> poll();

}
