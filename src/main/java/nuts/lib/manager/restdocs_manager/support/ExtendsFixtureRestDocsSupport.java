package nuts.lib.manager.restdocs_manager.support;

import nuts.lib.manager.fixture_manager.FixtureGenerateSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * It inherits from the controller test class using mockMvc.
 * <p>
 * This is a support that adds a test data generation extension function.
 *
 * @since 2024. 07. 09
 */
@ExtendWith(RestDocumentationExtension.class)
public abstract class ExtendsFixtureRestDocsSupport extends FixtureGenerateSupport {

    protected MockMvc mockController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {
        this.mockController = MockMvcBuilders.standaloneSetup(initController())
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider)).build();
    }

    protected abstract Object initController();
}
