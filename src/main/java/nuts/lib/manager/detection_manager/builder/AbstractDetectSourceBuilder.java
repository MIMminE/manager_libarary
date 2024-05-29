package nuts.lib.manager.detection_manager.builder;

import nuts.lib.manager.detection_manager.detector.JdbcDetector;
import org.springframework.security.core.parameters.P;

public abstract class AbstractDetectSourceBuilder {

    static public class DatabaseDetectBuilder implements DetectorBuilder<JdbcDetector>{
        public PostProcessPolicyBuilder defaultmode(){
            return new PostProcessPolicyBuilder();
        }

        public void custom(){

        }
    }

}