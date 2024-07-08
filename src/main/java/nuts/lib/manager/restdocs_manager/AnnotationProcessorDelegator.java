package nuts.lib.manager.restdocs_manager;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nuts.lib.manager.restdocs_manager.domain.expression.RestDocsAnnotationProcessor;
import nuts.lib.manager.restdocs_manager.domain.expression.FieldDescriptionProcessor;
import nuts.lib.manager.restdocs_manager.domain.expression.child.RestDocsChildDescriptionProcessor;
import nuts.lib.manager.restdocs_manager.domain.expression.child.ChildSectionProcessor;
import org.springframework.restdocs.payload.FieldDescriptor;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * It is designed to be used with the addition of multiple types of annotation processors.
 * You can extend its functionality by adding newly defined annotations and processors to this delegator class.
 *
 * @since 2024. 07. 08
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnnotationProcessorDelegator {

    static final public AnnotationProcessorDelegator INSTANCE = getInstance();
    static private AnnotationProcessorDelegator annotationProcessorDelegator;

    static private AnnotationProcessorDelegator getInstance() {
        if (annotationProcessorDelegator == null) return new AnnotationProcessorDelegator();
        else return annotationProcessorDelegator;
    }

    private final List<RestDocsAnnotationProcessor> annotationProcessors = List.of(new FieldDescriptionProcessor());
    private final List<RestDocsChildDescriptionProcessor> subSectionProcessors = List.of(new ChildSectionProcessor());

    public void handle(Annotation expressionAnnotation, List<FieldDescriptor> fieldDescriptors) {

        annotationProcessors.stream().filter(processor -> processor.support(expressionAnnotation)).findFirst()
                .orElseThrow(() -> new RuntimeException("%s no support processor annotation".formatted(expressionAnnotation)))
                .process(expressionAnnotation, fieldDescriptors, subSectionProcessors);
    }
}
