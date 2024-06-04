package nuts.lib.manager.verification_manager;

import java.util.function.Supplier;

public interface Verifier<T> {

    boolean condition();

    Supplier<T> postProcessing();
}
