package nuts.lib.manager.restdocs_manager.expression.child;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;

import java.lang.annotation.Annotation;
import java.util.List;

public class ChildSectionProcessor implements RestDocsChildDescriptionProcessor {

    @Override
    public void process(Annotation annotation, List<FieldDescriptor> fieldDescriptors) {
         ChildSection expressionField = (ChildSection) annotation;
         fieldDescriptors.add(generateSubsectionDescriptor(expressionField.name(), expressionField.description(), expressionField.optional()));
    }

    @Override
    public boolean support(Annotation annotation) {
        return annotation instanceof ChildSection;
    }

     private static FieldDescriptor generateSubsectionDescriptor(String name, String description, boolean optional) {

        return PayloadDocumentation.subsectionWithPath(name).description(description);
    }
}
