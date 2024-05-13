package nuts.lib.manager.restdocs_manager.docs_snippet;

import nuts.lib.manager.restdocs_manager.sub_section.SubSectionProcessor;
import org.springframework.restdocs.payload.FieldDescriptor;

import java.lang.annotation.Annotation;
import java.util.List;
public interface RestDocsAnnotationProcessor {
    void process(Annotation annotation, List<FieldDescriptor> fieldDescriptors ,List<SubSectionProcessor> subSectionProcessors);

    boolean support(Annotation annotation);
}
