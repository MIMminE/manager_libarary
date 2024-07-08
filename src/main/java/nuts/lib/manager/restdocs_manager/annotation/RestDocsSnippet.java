package nuts.lib.manager.restdocs_manager.annotation;

import nuts.lib.manager.restdocs_manager.expression.FieldDescription;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RestDocsSnippet {
    FieldDescription[] fields();
}
