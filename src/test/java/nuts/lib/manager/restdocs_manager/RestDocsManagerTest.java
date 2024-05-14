package nuts.lib.manager.restdocs_manager;

import nuts.lib.manager.restdocs_manager.target.RequestEX;
import nuts.lib.manager.restdocs_manager.target.Target;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.RequestFieldsSnippet;

class RestDocsManagerTest {

    private final RestDocsManager restDocsManager = new RestDocsManager(AnnotationProcessorDelegator.INSTANCE, Target.class, Target.class);

    @Test
    void test() throws NoSuchFieldException {
        // given
        restDocsManager.document("test", RequestEX.class, null);
//        RequestFieldsSnippet requestFieldsSnippet = restDocsManager.requestFieldsSnippet(null);

        // when

        // then
    }
}