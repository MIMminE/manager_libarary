package nuts.lib.manager.restdocs_manager.domain.expression.child;

import org.springframework.restdocs.payload.FieldDescriptor;

import java.lang.annotation.Annotation;
import java.util.List;

public interface RestDocsChildDescriptionProcessor {
    void process(Annotation annotation, List<FieldDescriptor> fieldDescriptors);

    boolean support(Annotation annotation);

}
