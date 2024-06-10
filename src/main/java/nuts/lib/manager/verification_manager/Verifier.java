package nuts.lib.manager.verification_manager;

import java.util.function.Supplier;

@Deprecated(since = "2024. 06. 10")
public interface Verifier<T> {

    boolean condition();

    Supplier<T> postProcessing();
}
