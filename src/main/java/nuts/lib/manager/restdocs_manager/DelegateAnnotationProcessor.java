//package nuts.lib.manager.restdocs_manager;
//
//import nuts.lib.manager.restdocs_manager.expression.DocsField;
//import nuts.lib.manager.restdocs_manager.processor.FieldProcessor;
//import org.springframework.restdocs.payload.FieldDescriptor;
//
//import java.lang.annotation.Annotation;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.stream.Stream;
//
//public class DelegateAnnotationProcessor {
//
//    static final public DelegateAnnotationProcessor INSTANCE = new DelegateAnnotationProcessor();
//
//    Map<Class<? extends Annotation>, RestDocsAnnotationProcessor> annotationProcessors
//            = Map.of(DocsField.class, new FieldProcessor());
//
//
//    public List<FieldDescriptor> mandate(Stream<Annotation> annotationStream, String type){
//        if (Objects.equals(type, "request")) {
//            annotationStream.map(annotation -> annotationProcessors.get(annotation).processRequestFieldSnippet())
//        }
//
//
//
//        return null;
//    }
//
//    private DelegateAnnotationProcessor() {
//    }
//}
