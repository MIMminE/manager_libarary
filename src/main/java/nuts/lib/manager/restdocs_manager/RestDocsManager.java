package nuts.lib.manager.restdocs_manager;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.web.servlet.ResultHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RestDocsManager<REQ, RES> {
    private final List<Class<?>> docsHolders;
    private final AnnotationProcessorDelegator annotationProcessorDelegator;

    public RestDocsManager(AnnotationProcessorDelegator annotationProcessorDelegator, Class<?> ...docsHolders) {
        this.annotationProcessorDelegator = annotationProcessorDelegator;
        this.docsHolders = Arrays.stream(docsHolders).collect(Collectors.toList());
    }

    private final RestDocsBuilder restDocsBuilder = new RestDocsBuilder();

    public RestDocsManager onPrettyPrint() {
        restDocsBuilder.setPrettyPrint(true);
        return this;
    }

    public ResultHandler document(String documentName, RequestFieldsSnippet requestFieldsSnippet, ResponseFieldsSnippet responseFieldsSnippet) {
        return this.createDocument(documentName, requestFieldsSnippet, responseFieldsSnippet);
    }

    public ResultHandler document(String documentName, ResponseFieldsSnippet responseFieldsSnippet) {
        return this.createDocument(documentName, responseFieldsSnippet);
    }

    public ResultHandler document(String documentName, Field docsRequestField, Field docsResponseField) {
        return this.createDocument(documentName, this.requestFieldsSnippet(docsRequestField), this.responseFieldsSnippet(docsResponseField));
    }

    public ResultHandler document(String documentName, Field docsResponseField) {
        return this.createDocument(documentName, this.responseFieldsSnippet(docsResponseField));
    }

    public ResultHandler document(String documentName, Object requestHolderField) {
        System.out.println(requestHolderField.hashCode());
        System.out.println(requestHolderField.getClass());
        System.out.println(requestHolderField).;
        return null;
    }


    public RequestFieldsSnippet requestFieldsSnippet(Field docsRequest) {
        if (docsRequest.getDeclaringClass().getAnnotation(DocsHolder.class).value() != DocsHolder.RestDocsHolderType.request)
            throw new IllegalArgumentException("@docsHolder's value is not request");

        DocsSnippet docsSnippet = docsRequest.getAnnotation(DocsSnippet.class);

        List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
        Arrays.stream(docsSnippet.fields()).forEach(expressionField
                -> annotationProcessorDelegator.handle(expressionField, fieldDescriptors));

        return createRequestFieldsSnippet(fieldDescriptors);
    }

    public ResponseFieldsSnippet responseFieldsSnippet(Field docsRequest) {
        if (docsRequest.getDeclaringClass().getAnnotation(DocsHolder.class).value() != DocsHolder.RestDocsHolderType.response)
            throw new IllegalArgumentException("@docsHolder's value is not response");

        DocsSnippet docsSnippet = docsRequest.getAnnotation(DocsSnippet.class);

        List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
        Arrays.stream(docsSnippet.fields()).forEach(expressionField
                -> annotationProcessorDelegator.handle(expressionField, fieldDescriptors));

        return createResponseFieldSnippet(fieldDescriptors);
    }

    private ResultHandler createDocument(String documentName, RequestFieldsSnippet requestFieldsSnippet, ResponseFieldsSnippet responseFieldsSnippet) {
        restDocsBuilder.setDocumentName(documentName);
        restDocsBuilder.setRequestFieldsSnippet(requestFieldsSnippet);
        restDocsBuilder.setResponseFieldsSnippet(responseFieldsSnippet);
        return restDocsBuilder.build();
    }

    private ResultHandler createDocument(String documentName, ResponseFieldsSnippet responseFieldsSnippet) {
        restDocsBuilder.setDocumentName(documentName);
        restDocsBuilder.setResponseFieldsSnippet(responseFieldsSnippet);
        return restDocsBuilder.build();
    }

    private static RequestFieldsSnippet createRequestFieldsSnippet(List<FieldDescriptor> fieldDescriptors) {
        return PayloadDocumentation.requestFields(fieldDescriptors);
    }

    private static ResponseFieldsSnippet createResponseFieldSnippet(List<FieldDescriptor> fieldDescriptors) {
        return PayloadDocumentation.responseFields(fieldDescriptors);
    }


}
