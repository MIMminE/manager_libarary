package nuts.lib.manager.verification_manager.annotation_verifier.impl;

import nuts.lib.manager.verification_manager.Verifier;
import nuts.lib.manager.verification_manager.annotation_verifier.Essential;

import java.lang.reflect.Field;
import java.util.function.Supplier;

/**
 * A class that checks whether the value of a field inside an instance is empty or not.
 * <p>
 * If the field with {@link Essential} annotations is empty, an exception is raised and sent to the manager.
 *
 * @author nuts
 * @since 2024. 06. 10
 */

@Deprecated(since = "2024. 06. 10")
public class AnnotationVerifier implements Verifier<RuntimeException> {
    private final Object instance;
    private final RuntimeException suppliedRuntimeException;

    public AnnotationVerifier(Object instance, RuntimeException suppliedRuntimeException) {
        this.instance = instance;
        this.suppliedRuntimeException = suppliedRuntimeException;
    }

    @Override
    public boolean condition() {

        for (Field field : instance.getClass().getDeclaredFields()) {
            Essential annotation = field.getAnnotation(Essential.class);
            if (annotation != null) {
                field.setAccessible(true);
                Object fieldValue;
                try {
                    fieldValue = field.get(instance);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                if (fieldValue == null) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Supplier<RuntimeException> postProcessing() {
        return () -> suppliedRuntimeException;
    }
}
