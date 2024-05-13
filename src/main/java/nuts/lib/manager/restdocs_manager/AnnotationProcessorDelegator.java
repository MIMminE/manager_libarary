package nuts.lib.manager.restdocs_manager;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nuts.lib.manager.restdocs_manager.docs_snippet.RestDocsAnnotationProcessor;
import nuts.lib.manager.restdocs_manager.docs_snippet.processor.ExpressionFieldProcessor;
import nuts.lib.manager.restdocs_manager.sub_section.SubSectionProcessor;
import nuts.lib.manager.restdocs_manager.sub_section.processor.ExpressionSubSectionProcessor;
import org.springframework.restdocs.payload.FieldDescriptor;

import java.lang.annotation.Annotation;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnnotationProcessorDelegator {

    static final public AnnotationProcessorDelegator INSTANCE = getInstance();
    static private AnnotationProcessorDelegator annotationProcessorDelegator;

    static private AnnotationProcessorDelegator getInstance() {
        if (annotationProcessorDelegator == null) return new AnnotationProcessorDelegator();
        else return annotationProcessorDelegator;
    }

    private final List<RestDocsAnnotationProcessor> annotationProcessors = List.of(new ExpressionFieldProcessor());
    private final List<SubSectionProcessor> subSectionProcessors = List.of(new ExpressionSubSectionProcessor());

    public void handle(Annotation expressionAnnotation, List<FieldDescriptor> fieldDescriptors) {

        annotationProcessors.stream().filter(processor -> processor.support(expressionAnnotation)).findFirst()
                .orElseThrow(() -> new RuntimeException("%s no support processor annotation".formatted(expressionAnnotation)))
                .process(expressionAnnotation, fieldDescriptors, subSectionProcessors);
    }
}
