package nuts.lib.manager.fixture_manager.extend;

import com.navercorp.fixturemonkey.FixtureMonkey;
import nuts.lib.manager.fixture_manager.FixtureManager;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class DynamicTestCaseSupport {
    private final FixtureMonkey fixtureMonkey = FixtureManager.supplierDefault.get();

    protected <T> T giveMe(Class<T> targetClass) {
        return fixtureMonkey.giveMeOne(targetClass);
    }

    protected <T> List<T> giveMe(Class<T> targetClass, int count) {
        return fixtureMonkey.giveMe(targetClass,count);
    }
}
