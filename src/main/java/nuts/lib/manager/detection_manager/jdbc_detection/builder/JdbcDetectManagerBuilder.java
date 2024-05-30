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
    JdbcDetectSourceConfigurer jdbcDetectSourceConfigurer = new JdbcDetectSourceConfigurer();
    JdbcDetectionProcessorConfigurer jdbcDetectionProcessorConfigurer = new JdbcDetectionProcessorConfigurer();
    JdbcPostProcessorConfigurer jdbcPostProcessorConfigurer = new JdbcPostProcessorConfigurer();


    public JdbcDetectManagerBuilder detectSource(Configurer<JdbcDetectSourceConfigurer> configurer) {
        configurer.config(jdbcDetectSourceConfigurer);
        return this;
    }

    public JdbcDetectManagerBuilder jdbcDetectionProcessor(Configurer<JdbcDetectionProcessorConfigurer> configurer) {
        configurer.config(jdbcDetectionProcessorConfigurer);
        return this;
    }

    public JdbcDetectManagerBuilder jdbcPostProcessor(Configurer<JdbcPostProcessorConfigurer> configurer) {
        configurer.config(jdbcPostProcessorConfigurer);
        return this;
    }

    public JdbcDetectionManager build(JdbcTemplate jdbcTemplate, int intervalMillis) {
        return new JdbcDetectionManager(jdbcTemplate, jdbcDetectSourceConfigurer.jdbcDetectSource,
                new JdbcDetectionProcessor(jdbcDetectionProcessorConfigurer.detectionProcessorPolicy),
                new JdbcPostProcessor(jdbcPostProcessorConfigurer.postProcessorPolicy), intervalMillis);
    }

}
