package nuts.lib.manager.restdocs_manager;

import nuts.lib.manager.restdocs_manager.target.Target;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.RequestFieldsSnippet;

class RestDocsManagerTest {

    private final RestDocsManager restDocsManager = new RestDocsManager(AnnotationProcessorDelegator.INSTANCE, Target.class);

    @Test
    void test() throws NoSuchFieldException {
        // given
        Target target = new Target();
//        RequestFieldsSnippet requestFieldsSnippet = restDocsManager.requestFieldsSnippet(Target.class.getDeclaredField("test"));

        restDocsManager.document("d",Target.test);



//        RequestFieldsSnippet requestFieldsSnippet = restDocsManager.requestFieldsSnippet(null);

        // when

        // then
    }
}