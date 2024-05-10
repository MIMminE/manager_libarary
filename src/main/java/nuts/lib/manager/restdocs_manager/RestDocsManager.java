package nuts.lib.manager.restdocs_manager;

import lombok.RequiredArgsConstructor;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class RestDocsManager {

//    private final DelegateAnnotationProcessor delegateAnnotationProcessor;

    public RequestFieldsSnippet requestFieldsSnippet(Field docsRequest) {

        Annotation[] annotations = docsRequest.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }


        Stream<Annotation> annotationStream = Arrays.stream(docsRequest.getAnnotations());


//        List<FieldDescriptor> fieldDescriptors = delegateAnnotationProcessor.mandate(annotationStream, "request");

        return null;
    }

    public ResponseFieldsSnippet responseFieldsSnippet(Field docsResponse) {
        return null;
    }
}
