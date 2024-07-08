package nuts.lib.manager.restdocs_manager.expression;

import nuts.lib.manager.restdocs_manager.expression.child.RestDocsChildDescriptionProcessor;
import org.springframework.restdocs.payload.FieldDescriptor;

import java.lang.annotation.Annotation;
import java.util.List;
public interface RestDocsAnnotationProcessor {
    void process(Annotation annotation, List<FieldDescriptor> fieldDescriptors ,List<RestDocsChildDescriptionProcessor> subSectionProcessors);

    boolean support(Annotation annotation);
}
