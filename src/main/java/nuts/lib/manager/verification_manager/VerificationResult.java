package nuts.lib.manager.verification_manager;

public interface VerificationResult<T> {

    T getResult();

    void addResult(T  result);
}
