package nuts.lib.manager.verification_manager.annotation_verifier;

import nuts.lib.commom.infra.Configurer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class AnnotationVerificationManagerBuilder {

    private List<AnnotationVerifierConfigurer> annotationInstanceVerifierConfigurers = new ArrayList<>();
    private Function<List<RuntimeException>, RuntimeException> resultProcessor;

    public AnnotationVerificationManagerBuilder verifier(Configurer<AnnotationVerifierConfigurer> configurer) {
        AnnotationVerifierConfigurer verifierConfigurer = new AnnotationVerifierConfigurer();
        configurer.config(verifierConfigurer);
        annotationInstanceVerifierConfigurers.add(verifierConfigurer);
        return this;
    }

    public AnnotationVerificationManagerBuilder verifier(List<AnnotationVerifierConfigurer> annotationInstanceVerifierConfigurers){
        this.annotationInstanceVerifierConfigurers = annotationInstanceVerifierConfigurers;
        return this;
    }

    public AnnotationVerificationManagerBuilder resultProcessor(Function<List<RuntimeException>, RuntimeException> resultProcessor) {
        this.resultProcessor = resultProcessor;
        return this;
    }

    public AnnotationVerificationManager build() {
        if (!annotationInstanceVerifierConfigurers.isEmpty() && resultProcessor != null) {
            List<AnnotationVerifier> instanceVerifiers = annotationInstanceVerifierConfigurers.stream().map(this::createVerifier).toList();

            return new AnnotationVerificationManager(instanceVerifiers, this.createVerifierResult(resultProcessor));
        }
        throw new IllegalStateException("The settings are incorrect.");
    }

    private AnnotationVerifier createVerifier(AnnotationVerifierConfigurer configurer) {
        return new AnnotationVerifier(configurer.getTargetInstance(), configurer.getResultRuntimeException());
    }

    private AnnotationVerifierResult createVerifierResult(Function<List<RuntimeException>, RuntimeException> resultProcessor) {
        return new AnnotationVerifierResult(resultProcessor);
    }
}
