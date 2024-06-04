package nuts.lib.manager.verification_manager.annotation_verifier;

import nuts.lib.manager.verification_manager.VerificationManager;
import nuts.lib.manager.verification_manager.Verifier;

import java.util.List;

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
