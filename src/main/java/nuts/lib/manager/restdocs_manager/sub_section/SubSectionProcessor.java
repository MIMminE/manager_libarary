package nuts.lib.manager.restdocs_manager.sub_section;

import org.springframework.restdocs.payload.FieldDescriptor;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.Supplier;

public interface SubSectionProcessor {
    void process(Annotation annotation, List<FieldDescriptor> fieldDescriptors);

    boolean support(Annotation annotation);

}
