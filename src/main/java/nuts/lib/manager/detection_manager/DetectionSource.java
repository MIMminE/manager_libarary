package nuts.lib.manager.detection_manager;

import nuts.lib.manager.detection_manager.jdbc_detection.AbstractJdbcDetectionSource;

import java.util.List;

/**
 * An interface for implementing data sensing for a change detection target (file or system).
 * <p>
 * The default implementation is {@link AbstractJdbcDetectionSource}.
 *
 * @param <T> : Define the type of detected data to receive.
 * @since 2024. 06. 20
 */
public interface DetectionSource<T> {

    List<T> poll();

}
