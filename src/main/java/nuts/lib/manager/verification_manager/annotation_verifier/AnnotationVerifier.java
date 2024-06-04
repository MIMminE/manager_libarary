package nuts.lib.manager.verification_manager.annotation_verifier;

import nuts.lib.manager.verification_manager.Verifier;

import java.lang.reflect.Field;
import java.util.function.Supplier;

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
