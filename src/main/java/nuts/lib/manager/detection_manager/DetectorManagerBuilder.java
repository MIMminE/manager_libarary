package nuts.lib.manager.detection_manager;

import nuts.lib.manager.detection_manager.builder.AbstractDetectSourceBuilder;
import nuts.lib.manager.detection_manager.builder.DetectorBuilder;
import nuts.lib.manager.detection_manager.detector.JdbcDetector;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class DetectorManagerBuilder {

    private Detector<?> detector;
    private DetectSource<?> detectSource;
    private PostProcessPolicy<?> postProcessPolicy;

    public static AbstractDetectSourceBuilder.DatabaseDetectBuilder databaseDetect(JdbcTemplate jdbcTemplate){
        return new AbstractDetectSourceBuilder.DatabaseDetectBuilder();
    }

}
