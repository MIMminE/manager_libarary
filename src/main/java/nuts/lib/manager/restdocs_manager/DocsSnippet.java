package nuts.lib.manager.restdocs_manager;

import nuts.lib.manager.restdocs_manager.docs_snippet.expression.ExpressionField;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DocsSnippet {
    ExpressionField[] fields();
}
