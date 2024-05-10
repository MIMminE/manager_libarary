package nuts.lib.manager.restdocs_manager;

import nuts.lib.manager.restdocs_manager.target.Target;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.RequestFieldsSnippet;

import static org.junit.jupiter.api.Assertions.*;

class RestDocsManagerTest {

    private final RestDocsManager restDocsManager = new RestDocsManager();

    @Test
    void test() throws NoSuchFieldException {
        // given
        Target target = new Target();
        restDocsManager.requestFieldsSnippet(Target.class.getDeclaredField("test"));

//        RequestFieldsSnippet requestFieldsSnippet = restDocsManager.requestFieldsSnippet(null);

        // when

        // then
    }
}