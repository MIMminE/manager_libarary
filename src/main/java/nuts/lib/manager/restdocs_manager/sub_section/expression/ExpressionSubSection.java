package nuts.lib.manager.restdocs_manager.sub_section.expression;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ExpressionSubSection {
    String name() default "";
    String description() default "";
    boolean optional() default false;

}
