package nuts.lib.commom.configurer;

import nuts.lib.manager.verification_manager.annotation_verifier.AnnotationVerificationManager;
import nuts.lib.manager.verification_manager.annotation_verifier.builder.AnnotationVerificationManagerBuilder;
import nuts.lib.manager.verification_manager.annotation_verifier.builder.AnnotationVerifierConfigurer;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class is designed to inherit from the builder-type class that has an instance of the Configurer type inside of it.
 * <p>
 * You need to implement one abstract method that returns a Map type,
 * which is used to create an instance of the {@link AnnotationVerificationManager}.
 * <p>
 * If the member variables of an instance of the Configurer type have {@link nuts.lib.manager.verification_manager.annotation_verifier.Essential} annotations,
 * <p>
 * It means that the member variables are valued and are required to be used during the build process of the builder class.
 * <pre>
 *
 * {@code
 * public class UserTableInfoConfigurer {
 *     @Essential
 *     private String tableName;
 *
 *     @Essential
 *     private String userNameField;
 *
 *     @Essential
 *     private String passWordField;
 * }
 * }
 * </pre>
 * <p>
 * After inheriting the class,
 * it passes the configuration class instance and the exception message that will occur when the instance tries to build without the required values, as shown below.
 *
 * <pre>
 * {@code
 * @Override
 * protected Supplier<Map<Object, String>> setVerification() {
 *     return () -> Map.of(
 *             this.userTableInfoConfigurer,
 *             "userTableInfoConfigurer [ Method : userTableInfo ]",
 *             this.authorityTableInfoConfigurer,
 *             "authorityTableInfoConfigurer [ Method : authorityTableInfo ]");
 * }
 * }
 *
 * </pre>
 *
 * @author nuts
 * @since 2024. 06. 04
 */
public abstract class AnnotationVerificationBuilder {

    protected AnnotationVerificationManager verificationManager;

    protected void verify() {
        if (this.verificationManager == null) createVerificationManager(setVerification().get());

        RuntimeException verificationResult = this.verificationManager.getVerificationResult();
        if (verificationResult != null)
            throw verificationResult;
    }

    protected abstract Supplier<Map<Object, String>> setVerification();


    protected void createVerificationManager(Map<Object, String> verificationMap) {

        List<AnnotationVerifierConfigurer> verifierConfigurers = verificationMap.entrySet().stream().map(e ->
                new AnnotationVerifierConfigurer().targetInstance(e.getKey()).resultRuntimeException(new VerificationException(e.getValue()))).toList();


        Function<List<RuntimeException>, RuntimeException> resultProcessor = exceptions -> {
            String exceptionMessage = "The following configurer is missing a required value: | -> ";
            for (RuntimeException exception : exceptions)
                exceptionMessage += exception.getMessage() + " | ";
            return new VerificationException(exceptionMessage);
        };


        this.verificationManager = new AnnotationVerificationManagerBuilder()
                .verifier(verifierConfigurers).resultProcessor(resultProcessor).build();
    }
}
