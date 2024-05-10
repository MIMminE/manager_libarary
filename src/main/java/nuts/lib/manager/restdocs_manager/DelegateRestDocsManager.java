//package nuts.lib.manager.restdocs_manager;
//
//import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
//import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
//import org.springframework.restdocs.payload.RequestFieldsSnippet;
//import org.springframework.restdocs.payload.ResponseFieldsSnippet;
//
//import java.util.List;
//import java.util.function.Supplier;
//
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
//
//public class DelegateRestDocsManager {
//
//    static private List<RestDocsFactory<?>> getFactories(){
//        return List.of(new MapRestDocsFactory());
//    }
//
//    List<RestDocsFactory<?>> restDocsFactoryList = getFactories();
//    Supplier<RequestFieldsSnippet> supplierRequestFieldsSnippet = null;
//    Supplier<ResponseFieldsSnippet> supplierResponseFieldsSnippet = null;
//
//    public RestDocumentationResultHandler document(String identifier, Object request, Object response) {
//
//        for (RestDocsFactory<?> restDocsFactory : restDocsFactoryList) {
//
//            if (supplierRequestFieldsSnippet == null && restDocsFactory.support(request)) {
//                supplierRequestFieldsSnippet = restDocsFactory.requestFields(request);
//            }
//
//            if (supplierResponseFieldsSnippet == null && restDocsFactory.support(response)) {
//                supplierResponseFieldsSnippet = restDocsFactory.responseFields(response);
//            }
//
//            if (supplierRequestFieldsSnippet != null && supplierResponseFieldsSnippet != null) {
//                return createDocs(identifier, supplierRequestFieldsSnippet, supplierResponseFieldsSnippet);
//            }
//        }
//
//        throw new RuntimeException("No support argument type");
//    }
//
//    private RestDocumentationResultHandler createDocs(String identifier,
//                                                      Supplier<RequestFieldsSnippet> supplierRequestFieldsSnippet,
//                                                      Supplier<ResponseFieldsSnippet> supplierResponseFieldsSnippet) {
//
//        return MockMvcRestDocumentation.document(identifier,
//                preprocessRequest(prettyPrint()),
//                preprocessResponse(prettyPrint()),
//                supplierRequestFieldsSnippet.get(),
//                supplierResponseFieldsSnippet.get());
//    }
//}
