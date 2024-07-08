package nuts.lib.manager.restdocs_manager.expression;

import nuts.lib.manager.restdocs_manager.expression.child.ChildSection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface FieldDescription {
    String name();
    String description();
    boolean optional() default false;
    ChildSection[] subSections() default @ChildSection;
}
