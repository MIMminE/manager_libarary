package nuts.lib.manager.verification_manager.annotation_verifier;

import nuts.lib.manager.verification_manager.VerificationManager;
import nuts.lib.manager.verification_manager.Verifier;

import java.util.List;

/**
 * This is the default VerificationManager that returns {@link RuntimeException}.
 * <p>
 * It checks an instance of a class that uses the {@link Essential} annotation in a member variable and throws an exception if its value is empty.
 * <p>
 * This Manager can have multiple {@link AnnotationVerificationManager}, and exceptions can be thrown on each validation.
 * When multiple exceptions occur, the exception messages are consolidated and converted into a single exception.
 * <p>
 * You can use the {@link AnnotationVerificationManagerBuilder} class to easily do the whole setup.
 *
 * @author nuts
 * @since 2024. 06. 04
 */

public class AnnotationVerificationManager implements VerificationManager<RuntimeException> {

    List<AnnotationVerifier> verifiers;

    AnnotationVerifierResult verificationResult;

    public AnnotationVerificationManager(List<AnnotationVerifier> verifiers, AnnotationVerifierResult verificationResult) {
        this.verifiers = verifiers;
        this.verificationResult = verificationResult;
    }

    @Override
    public RuntimeException getVerificationResult() {
        this.apply();
        return verificationResult.getResult();
    }

    private void apply() {
        for (Verifier<RuntimeException> verifier : verifiers) {
            if (!verifier.condition())
                verificationResult.addResult(verifier.postProcessing().get());
        }
    }
}
