package nuts.lib.manager.verification_manager.annotation_verifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use it for a member variable in a configuration class to explicitly indicate that the member variable is required during the build process for a builder class that uses that configuration class.
 * <p>
 * If the builder class implements AnnotationVerificationBuilder, it will throw an exception during the build process.
 *
 * @author nuts
 * @since 2024. 06. 04
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Essential {
}
