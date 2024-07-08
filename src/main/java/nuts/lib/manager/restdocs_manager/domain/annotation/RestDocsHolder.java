package nuts.lib.manager.restdocs_manager.domain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RestDocsHolder {
    RestDocsHolderType value();

    enum RestDocsHolderType {
        request,
        response
    }
}
