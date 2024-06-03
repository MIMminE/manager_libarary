package nuts.lib.manager.detection_manager.jdbc_detection.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nuts.lib.commom.infra.Configurer;
import nuts.lib.manager.detection_manager.jdbc_detection.JdbcDetectionManager;
import nuts.lib.manager.detection_manager.jdbc_detection.JdbcDetectionProcessor;
import nuts.lib.manager.detection_manager.jdbc_detection.JdbcPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JdbcDetectManagerBuilder {

    public static JdbcDetectManagerBuilder builder = new JdbcDetectManagerBuilder();
    JdbcDetectSourceConfigurer jdbcDetectSourceConfigurer;
    JdbcDetectionProcessorConfigurer jdbcDetectionProcessorConfigurer;
    JdbcPostProcessorConfigurer jdbcPostProcessorConfigurer;


    public JdbcDetectManagerBuilder detectSource(Configurer<JdbcDetectSourceConfigurer> configurer) {
        this.jdbcDetectSourceConfigurer = new JdbcDetectSourceConfigurer();
        configurer.config(jdbcDetectSourceConfigurer);
        return this;
    }

    public JdbcDetectManagerBuilder jdbcDetectionProcessor(Configurer<JdbcDetectionProcessorConfigurer> configurer) {
        this.jdbcDetectionProcessorConfigurer = new JdbcDetectionProcessorConfigurer();
        configurer.config(jdbcDetectionProcessorConfigurer);
        return this;
    }

    public JdbcDetectManagerBuilder jdbcPostProcessor(Configurer<JdbcPostProcessorConfigurer> configurer) {
        this.jdbcPostProcessorConfigurer = new JdbcPostProcessorConfigurer();
        configurer.config(jdbcPostProcessorConfigurer);
        return this;
    }

    public JdbcDetectionManager build(JdbcTemplate jdbcTemplate, int intervalMillis) {
        if (jdbcDetectSourceConfigurer == null || jdbcDetectionProcessorConfigurer == null || jdbcPostProcessorConfigurer == null)
            throw new IllegalStateException("detectSource, jdbcDetectionProcessor, jdbcPostProcessor are required.");

        return new JdbcDetectionManager(jdbcTemplate, jdbcDetectSourceConfigurer.jdbcDetectSource,
                new JdbcDetectionProcessor(jdbcDetectionProcessorConfigurer.detectionProcessorPolicy),
                new JdbcPostProcessor(jdbcPostProcessorConfigurer.postProcessorPolicy), intervalMillis);
    }

}
