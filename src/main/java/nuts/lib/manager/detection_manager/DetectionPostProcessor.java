package nuts.lib.manager.detection_manager;

import nuts.lib.manager.detection_manager.jdbc_detection.AbstractJdbcDetectionPostProcessor;

import java.util.List;

/**
 * An interface that defines the subsequent processing of detected data.
 * <p>
 * The default implementation is {@link AbstractJdbcDetectionPostProcessor}
 *
 * @param <T> : Define the type of data to be passed by the {@link DetectionSource}.
 * @since 2024. 06. 20
 */
public interface DetectionPostProcessor<T> {

    void process(List<T> detectedObjects);
}
