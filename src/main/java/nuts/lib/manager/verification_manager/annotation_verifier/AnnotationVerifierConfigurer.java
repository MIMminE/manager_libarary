package nuts.lib.manager.verification_manager.annotation_verifier;

import lombok.Getter;

@Getter
public class AnnotationVerifierConfigurer {

    private Object targetInstance;
    private RuntimeException resultRuntimeException;

    public AnnotationVerifierConfigurer targetInstance(Object targetInstance){
        this.targetInstance = targetInstance;
        return this;
    }

    public AnnotationVerifierConfigurer resultRuntimeException(RuntimeException runtimeException){
        this.resultRuntimeException = runtimeException;
        return this;
    }
}
