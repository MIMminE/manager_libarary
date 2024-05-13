package nuts.lib.manager.restdocs_manager.sub_section.processor;

import nuts.lib.manager.restdocs_manager.docs_snippet.RestDocsAnnotationProcessor;
import nuts.lib.manager.restdocs_manager.sub_section.SubSectionProcessor;
import nuts.lib.manager.restdocs_manager.sub_section.expression.ExpressionSubSection;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.Supplier;

public class ExpressionSubSectionProcessor implements SubSectionProcessor {

    @Override
    public void process(Annotation annotation, List<FieldDescriptor> fieldDescriptors) {
         ExpressionSubSection expressionField = (ExpressionSubSection) annotation;
         fieldDescriptors.add(generateSubsectionDescriptor(expressionField.name(), expressionField.description(), expressionField.optional()));
    }

    @Override
    public boolean support(Annotation annotation) {
        return annotation instanceof ExpressionSubSection;
    }

     private static FieldDescriptor generateSubsectionDescriptor(String name, String description, boolean optional) {

        return PayloadDocumentation.subsectionWithPath(name).description(description);
    }
}
