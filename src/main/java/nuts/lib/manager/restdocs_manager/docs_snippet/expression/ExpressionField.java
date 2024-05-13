package nuts.lib.manager.restdocs_manager.docs_snippet.expression;

import nuts.lib.manager.restdocs_manager.sub_section.expression.ExpressionSubSection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ExpressionField {
    String name();
    String description();
    boolean optional() default false;
    ExpressionSubSection[] subSections() default @ExpressionSubSection;
}
