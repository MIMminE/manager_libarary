package nuts.lib.manager.detection_manager.detectors;

import nuts.lib.manager.detection_manager.Detector;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TableDetector extends Detector<Map<String, Object>> {
    @Override
    protected Predicate<Map<String, Object>> setPredicate() {
        return m -> m.get("id") != null;
    }

    @Override
    protected Consumer<Map<String, Object>> setConsumer() {
        return System.out::println;
    }

    @Override
    public boolean condition(Map<String, Object> conditionValue) {
        return super.condition(conditionValue);
    }

    @Override
    public void apply(Map<String, Object> callback) {
        super.apply(callback);
    }
}
