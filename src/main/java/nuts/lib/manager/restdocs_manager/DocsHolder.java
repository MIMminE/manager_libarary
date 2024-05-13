package nuts.lib.manager.restdocs_manager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DocsHolder {
    RestDocsHolderType value();

    enum RestDocsHolderType {
        request,
        response
    }
}
