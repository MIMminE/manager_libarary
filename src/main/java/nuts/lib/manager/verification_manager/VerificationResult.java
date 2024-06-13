package nuts.lib.manager.verification_manager;

/**
 * This is the interface that determines how to return the results of the internal validation of the class.
 * <p>
 * It can be runtime exceptions, lists, strings, etc.
 *
 * @creation 2024. 06. 13
 */
public interface VerificationResult<T> {

    T getResult();

    void addResult(T result);
}
