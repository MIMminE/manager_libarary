package nuts.lib.manager.verification_manager.annotation_verifier.impl;

import nuts.lib.commom.configurer.AnnotationVerificationBuilder;
import nuts.lib.manager.verification_manager.annotation_verifier.Essential;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * A class that checks whether the value of a field inside an instance is empty or not.
 * <p>
 * If the field with {@link Essential} annotations is empty, save the field name and send it to the manager.
 * <p>
 * It is used in {@link AnnotationVerificationBuilder}.
 *
 * @author nuts
 * @since 2024. 06. 10
 */
public class AnnotationPrintVerifier {

    private final Object instance;
    private final RuntimeException suppliedRuntimeException;

    public AnnotationPrintVerifier(Object instance, RuntimeException suppliedRuntimeException) {
        this.instance = instance;
        this.suppliedRuntimeException = suppliedRuntimeException;
    }

    public List<String> condition() {

        List<String> results = new ArrayList<>();

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
                    results.add(field.getName());
                }
            }
        }
        return results;
    }

    public Supplier<RuntimeException> postProcessing() {
        return () -> suppliedRuntimeException;
    }
}
