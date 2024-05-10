package nuts.lib.manager.restdocs_manager;

import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import java.util.function.Supplier;

public interface RestDocsAnnotationProcessor {
    Supplier<RequestFieldsSnippet> processRequestFieldSnippet(Object metaInfo);

    Supplier<ResponseFieldsSnippet> processResponseFieldSnippet(Object metaInfo);

}
