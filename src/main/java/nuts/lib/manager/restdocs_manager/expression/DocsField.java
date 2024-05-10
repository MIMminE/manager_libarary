package nuts.lib.manager.restdocs_manager.expression;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface DocsField {
    String name();
    String description();
    boolean optional() default false;
    DocsSubSection[] subSections() default @DocsSubSection;
}
