package nuts.lib.manager.detection_manager;

import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class Detector<T> {

    private String detectorName;
    private final Predicate<T> condition = setPredicate();
    private final Consumer<T> callback = setConsumer();

    protected abstract Predicate<T> setPredicate();
    protected abstract Consumer<T> setConsumer();

    public boolean condition(T conditionValue){
        return this.condition.test(conditionValue);
    }

    public void apply(T callback){
        this.callback.accept(callback);
    }
}