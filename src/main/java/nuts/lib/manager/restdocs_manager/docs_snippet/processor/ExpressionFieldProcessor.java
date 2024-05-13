package nuts.lib.manager.restdocs_manager.docs_snippet.processor;

import lombok.RequiredArgsConstructor;
import nuts.lib.manager.restdocs_manager.docs_snippet.expression.ExpressionField;
import nuts.lib.manager.restdocs_manager.docs_snippet.RestDocsAnnotationProcessor;
import nuts.lib.manager.restdocs_manager.sub_section.SubSectionProcessor;
import nuts.lib.manager.restdocs_manager.sub_section.expression.ExpressionSubSection;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class ExpressionFieldProcessor implements RestDocsAnnotationProcessor {

    @Override
    public void process(Annotation annotation, List<FieldDescriptor> fieldDescriptors, List<SubSectionProcessor> processors) {
        ExpressionField expressionField = (ExpressionField) annotation;
        fieldDescriptors.add(generateFieldDescriptor(expressionField.name(), expressionField.description(), expressionField.optional()));

        if (requiredIntrospect.test(((ExpressionField) annotation).subSections())) {
            ExpressionSubSection[] expressionSubSections = ((ExpressionField) annotation).subSections();
            for (ExpressionSubSection expressionSubSection : expressionSubSections) {
                for (SubSectionProcessor processor : processors) {
                    if (processor.support(expressionSubSection)) {
                        processor.process(expressionSubSection, fieldDescriptors);
                    }
                }
            }
        }
    }

    @Override
    public boolean support(Annotation annotation) {
        return annotation instanceof ExpressionField;
    }

    private static FieldDescriptor generateFieldDescriptor(String name, String description, boolean optional) {
        return PayloadDocumentation.fieldWithPath(name).description(description);
    }

    private static final Predicate<ExpressionSubSection[]> requiredIntrospect = sections
            -> sections.length != 1 && Arrays.stream(sections).anyMatch(e -> !Objects.equals(e.name(), ""));

}

