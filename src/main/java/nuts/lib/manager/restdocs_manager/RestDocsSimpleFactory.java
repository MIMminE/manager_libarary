//package nuts.lib.manager.restdocs_manager.factory;
//
//import lombok.extern.slf4j.Slf4j;
//import nuts.lib.manager.restdocs_manager.RestDocsFactory;
//import nuts.lib.manager.restdocs_manager.expression_annotation.DocsField;
//import nuts.lib.manager.restdocs_manager.RestDocsHolder;
//import nuts.lib.manager.restdocs_manager.RestDocsSnippet;
//import org.springframework.restdocs.payload.FieldDescriptor;
//import org.springframework.restdocs.payload.PayloadDocumentation;
//import org.springframework.restdocs.payload.RequestFieldsSnippet;
//import org.springframework.restdocs.payload.ResponseFieldsSnippet;
//
//import java.lang.reflect.Field;
//import java.util.HashSet;
//import java.util.Objects;
//import java.util.Set;
//import java.util.function.Function;
//
//
//@Slf4j
//public class RestDocsSimpleFactory implements RestDocsFactory<Class<?>> {
//
//    Set<Class<?>> requestSet = new HashSet<>();
//    Set<Class<?>> responseSet = new HashSet<>();
//
//    public RestDocsSimpleFactory(Iterable<Class<?>> classes) {
//        for (Class<?> aClass : classes) {
//            RestDocsHolder annotation = aClass.getAnnotation(RestDocsHolder.class);
//            try {
//                switch (annotation.value()) {
//                    case request -> requestSet.add(aClass);
//                    case response -> responseSet.add(aClass);
//                    default -> log.info("Use Annotation 'RestDocsHolder' :: {}", aClass);
//                }
//            } catch (NullPointerException e) {
//                log.debug("Use Annotation 'RestDocsHolder' :: {}", aClass);
//                throw e;
//            }
//        }
//    }
//
//    @Override
//    public RequestFieldsSnippet requestFields(Class<?> requestClass) {
//
//        for (Class<?> aClass : requestSet) {
//            Field[] declaredFields = aClass.getDeclaredFields();
//            for (Field declaredField : declaredFields) {
//                if (declaredField.getType().isAssignableFrom(requestClass)) {
//                    RequestFieldsSnippet result = PayloadDocumentation.requestFields();
//                    Function<RequestFieldsSnippet, RequestFieldsSnippet> snippetFunction = null;
//
//                    if (declaredField.isAnnotationPresent(RestDocsSnippet.class)) {
//                        RestDocsSnippet requestSnippet = declaredField.getAnnotation(RestDocsSnippet.class);
//
//                        snippetFunction = snippetFunction(requestSnippet);
//                    }
//                    return Objects.requireNonNull(snippetFunction).apply(result);
//                }
//            }
//        }
//
//        throw new RuntimeException("Bad Request Holder");
//    }
//
//    @Override
//    public ResponseFieldsSnippet responseFields(Class<?> responesClass) {
//        for (Class<?> aClass : responseSet) {
//            Field[] declaredFields = aClass.getDeclaredFields();
//            for (Field declaredField : declaredFields) {
//                if (declaredField.getType().isAssignableFrom(responesClass)) {
//                    ResponseFieldsSnippet result = PayloadDocumentation.responseFields();
//                    Function<ResponseFieldsSnippet, ResponseFieldsSnippet> snippetFunction = null;
//
//                    if (declaredField.isAnnotationPresent(RestDocsSnippet.class)) {
//                        RestDocsSnippet responseSnippet = declaredField.getAnnotation(RestDocsSnippet.class);
//                        snippetFunction = responseSnippetFunction(responseSnippet);
//                    }
//                    return Objects.requireNonNull(snippetFunction).apply(result);
//                }
//            }
//        }
//
//        throw new RuntimeException("Bad Response Holder");
//    }
//
//    private Function<ResponseFieldsSnippet, ResponseFieldsSnippet> responseSnippetFunction(RestDocsSnippet responseSnippet) {
//        Function<ResponseFieldsSnippet, ResponseFieldsSnippet> snippetFunction;
//        snippetFunction = snippet -> {
//            for (DocsField v : responseSnippet.value()) {
//                snippet = snippet.and(getDescription(v));
//            }
//            return snippet;
//        };
//        return snippetFunction;
//    }
//
//
//    private Function<RequestFieldsSnippet, RequestFieldsSnippet> snippetFunction(RestDocsSnippet requestSnippet) {
//        Function<RequestFieldsSnippet, RequestFieldsSnippet> snippetFunction;
//        snippetFunction = snippet -> {
//            for (DocsField v : requestSnippet.value()) {
//                snippet = snippet.and(getDescription(v));
//            }
//            return snippet;
//        };
//        return snippetFunction;
//    }
//
//    private FieldDescriptor getDescription(DocsField v) {
//
//        FieldDescriptor description = PayloadDocumentation
//                .fieldWithPath(v.name())
//                .description(v.description());
//
//        if (v.optional())
//            description.optional();
//
//        return description;
//    }
//}
