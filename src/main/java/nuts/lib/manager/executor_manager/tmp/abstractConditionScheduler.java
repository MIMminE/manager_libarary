package nuts.lib.manager.executor_manager.tmp;

public abstract class abstractConditionScheduler<T> {
    protected boolean condition = false;
    T taskSupplier = initTaskSupplier();

    protected abstract T initTaskSupplier();
}
