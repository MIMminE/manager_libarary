package nuts.lib.commom.configurer;

import nuts.lib.manager.verification_manager.VerificationManagerBuilder;
import nuts.lib.manager.verification_manager.annotation_verifier.AnnotationVerificationManager;
import nuts.lib.manager.verification_manager.annotation_verifier.AnnotationVerifierConfigurer;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AnnotationVerificationBuilder {

    protected AnnotationVerificationManager verificationManager;

    protected void verify() {
        if (this.verificationManager == null) createVerificationManager(setVerification().get());

        RuntimeException verificationResult = this.verificationManager.getVerificationResult();
        if (verificationResult != null)
            throw verificationResult;
    }

    protected abstract Supplier<Map<Object, String>> setVerification();

    private void createVerificationManager(Map<Object, String> verificationMap) {

        List<AnnotationVerifierConfigurer> verifierConfigurers = verificationMap.entrySet().stream().map(e ->
                new AnnotationVerifierConfigurer().targetInstance(e.getKey()).resultRuntimeException(new VerificationException(e.getValue()))).toList();


        Function<List<RuntimeException>, RuntimeException> resultProcessor = exceptions -> {
            String exceptionMessage = "The following configurer is missing a required value: | -> ";
            for (RuntimeException exception : exceptions)
                exceptionMessage += exception.getMessage() + " | ";
            return new VerificationException(exceptionMessage);
        };


        this.verificationManager = VerificationManagerBuilder.annotationVerifierConfig
                .verifier(verifierConfigurers).resultProcessor(resultProcessor).build();
    }
}
