package nuts.lib.manager.restdocs_manager;

import lombok.Setter;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.web.servlet.ResultHandler;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@Setter
public class RestDocsFactory {
    private String documentName;
    private RequestFieldsSnippet requestFieldsSnippet;
    private ResponseFieldsSnippet responseFieldsSnippet;
    private Boolean prettyPrint = true;

    public ResultHandler build() {

        if (prettyPrint && requestFieldsSnippet != null) {
            return MockMvcRestDocumentation.document(documentName,
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFieldsSnippet,
                    responseFieldsSnippet);
        }
        if (prettyPrint) {
            return MockMvcRestDocumentation.document(documentName,
                    preprocessResponse(prettyPrint()),
                    responseFieldsSnippet);
        }

        return defaultDocs();
    }

    private ResultHandler defaultDocs() {
        if (requestFieldsSnippet == null)
            return MockMvcRestDocumentation.document(documentName, responseFieldsSnippet);

        return MockMvcRestDocumentation.document(documentName,
                requestFieldsSnippet,
                responseFieldsSnippet);
    }

}
