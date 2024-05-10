package nuts.lib.manager.restdocs_manager.processor;

import nuts.lib.manager.restdocs_manager.RestDocsAnnotationProcessor;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import java.util.function.Supplier;

public class FieldProcessor implements RestDocsAnnotationProcessor {

    @Override
    public Supplier<RequestFieldsSnippet> processRequestFieldSnippet(Object metaInfo) {
        return null;
    }

    @Override
    public Supplier<ResponseFieldsSnippet> processResponseFieldSnippet(Object metaInfo) {
        return null;
    }


}
