package nuts.lib.commom.configurer;

@FunctionalInterface
public interface Configurer<T> {
    void config(T t);

    static <T> Configurer<T> withDefault() {
        return (t) -> {
        };
    }
}
