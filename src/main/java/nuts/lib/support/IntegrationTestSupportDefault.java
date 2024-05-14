package nuts.lib.support;

import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ActiveProfiles("Integration Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class IntegrationTestSupportDefault {

}