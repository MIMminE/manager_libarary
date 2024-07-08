package nuts.lib.manager.restdocs_manager.expression;

import lombok.RequiredArgsConstructor;
import nuts.lib.manager.restdocs_manager.expression.child.RestDocsChildDescriptionProcessor;
import nuts.lib.manager.restdocs_manager.expression.child.ChildSection;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class FieldDescriptionProcessor implements RestDocsAnnotationProcessor {

    @Override
    public void process(Annotation annotation, List<FieldDescriptor> fieldDescriptors, List<RestDocsChildDescriptionProcessor> processors) {
        FieldDescription expressionField = (FieldDescription) annotation;
        fieldDescriptors.add(generateFieldDescriptor(expressionField.name(), expressionField.description(), expressionField.optional()));

        if (requiredIntrospect.test(((FieldDescription) annotation).subSections())) {
            ChildSection[] expressionSubSections = ((FieldDescription) annotation).subSections();
            for (ChildSection expressionSubSection : expressionSubSections) {
                for (RestDocsChildDescriptionProcessor processor : processors) {
                    if (processor.support(expressionSubSection)) {
                        processor.process(expressionSubSection, fieldDescriptors);
                    }
                }
            }
        }
    }

    @Override
    public boolean support(Annotation annotation) {
        return annotation instanceof FieldDescription;
    }

    private static FieldDescriptor generateFieldDescriptor(String name, String description, boolean optional) {
        return PayloadDocumentation.fieldWithPath(name).description(description);
    }

    private static final Predicate<ChildSection[]> requiredIntrospect = sections
            -> sections.length != 1 && Arrays.stream(sections).anyMatch(e -> !Objects.equals(e.name(), ""));

}

