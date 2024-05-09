package nuts.lib.manager.rest_docs_manager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RestDocsHolder {
    RestDocsHolderType value() default RestDocsHolderType.request;

    enum RestDocsHolderType {
        request,
        response
    }
}
