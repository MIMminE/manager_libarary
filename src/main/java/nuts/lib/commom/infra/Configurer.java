package nuts.lib.commom.infra;

@FunctionalInterface
public interface Configurer<T> {
    void config(T t);
}
