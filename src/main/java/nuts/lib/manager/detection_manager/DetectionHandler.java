package nuts.lib.manager.detection_manager;


import nuts.lib.manager.detection_manager.jdbc_detection.AbstractJdbcDetectionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * An interface that implements the ability to process data detected and passed by {@link DetectionSource}.
 * <p>
 * The default implementation is {@link AbstractJdbcDetectionHandler}
 *
 * @param <T> : Define the type of data to be passed by the {@link DetectionSource}.
 * @since 2024. 06. 20
 */
public interface DetectionHandler<T> {

    void process(List<T> detectedObjects);

    /**
     * A good function to use when splitting detected lists into multiple.
     * @since 2024. 06. 25
     */
    default List<List<T>> splitDetectedObjects(List<T> list, int n) {
        List<List<T>> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i += n) {
            result.add(new ArrayList<>(list.subList(i, Math.min(i + n, list.size()))));
        }
        return result;
    }
}
