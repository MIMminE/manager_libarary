package nuts.lib.manager.verification_manager.annotation_verifier;

import nuts.lib.commom.infra.Configurer;
import nuts.lib.manager.verification_manager.annotation_verifier.impl.AnnotationPrintVerifier;
import nuts.lib.manager.verification_manager.annotation_verifier.impl.AnnotationVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * This is a {@link AnnotationVerificationManager} builder class.
 * <p>
 * Set up a configuration instance to create each AnnotationVerifier and a Function instance to incorporate the exceptions from each instance.
 *
 * <pre>
 * {@code
 * VerificationManagerBuilder.annotationVerifierConfig.verifier(
 *      config -> config.targetInstance(authorityTableInfoConfigurer)
 *          .resultRuntimeException(new RuntimeException("authorityTableInfoConfigurer")))
 *          .resultProcessor(exceptions -> {
 *               String exceptionMessage = "The following settings are not fully met. | -> ";
 *               for (RuntimeException exception : exceptions)
 *                   exceptionMessage += exception.getMessage() + " ";
 *               return new RuntimeException(exceptionMessage);
 *           }
 *   )
 *       .build();
 * }
 * </pre>
 *
 * @author nuts
 * @since 2024. 06. 04
 */
public class AnnotationVerificationManagerBuilder {

    private List<AnnotationVerifierConfigurer> annotationInstanceVerifierConfigurers = new ArrayList<>();
    private Function<List<RuntimeException>, RuntimeException> resultProcessor;

    public AnnotationVerificationManagerBuilder verifier(Configurer<AnnotationVerifierConfigurer> configurer) {
        AnnotationVerifierConfigurer verifierConfigurer = new AnnotationVerifierConfigurer();
        configurer.config(verifierConfigurer);
        annotationInstanceVerifierConfigurers.add(verifierConfigurer);
        return this;
    }

    public AnnotationVerificationManagerBuilder verifier(List<AnnotationVerifierConfigurer> annotationInstanceVerifierConfigurers) {
        this.annotationInstanceVerifierConfigurers = annotationInstanceVerifierConfigurers;
        return this;
    }

    public AnnotationVerificationManagerBuilder resultProcessor(Function<List<RuntimeException>, RuntimeException> resultProcessor) {
        this.resultProcessor = resultProcessor;
        return this;
    }

    public AnnotationVerificationManager build() {
        if (!annotationInstanceVerifierConfigurers.isEmpty() && resultProcessor != null) {
            List<AnnotationPrintVerifier> instanceVerifiers = annotationInstanceVerifierConfigurers.stream().map(this::createVerifier).toList();

            return new AnnotationVerificationManager(instanceVerifiers, this.createVerifierResult(resultProcessor));
        }
        throw new IllegalStateException("The settings are incorrect.");
    }

    private AnnotationPrintVerifier createVerifier(AnnotationVerifierConfigurer configurer) {
        return new AnnotationPrintVerifier(configurer.getTargetInstance(), configurer.getResultRuntimeException());
    }

    private AnnotationVerifierResult createVerifierResult(Function<List<RuntimeException>, RuntimeException> resultProcessor) {
        return new AnnotationVerifierResult(resultProcessor);
    }
}
