package nuts.lib.manager.fixture_manager;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class FixtureGenerateSupport {
    private final FixtureMonkey fixtureMonkey = FixtureManager.supplierDefault.get();

    private final Map<Class<?>, List<?>> orderedObjectMap = init(ordersObject());

    private Map<Class<?>, List<?>> init(List<OrderSheet> orderSheets) {

        Map<Class<?>, List<?>> result = new ConcurrentHashMap<>();

        orderSheets.stream().filter(orderSheet -> orderSheet.getCount() == 1 && orderSheet.getArbitraryBuilder() == null)
                .forEach(orderSheet -> result.put(orderSheet.getOrderClass(), List.of(fixtureMonkey.giveMeOne(orderSheet.getOrderClass()))));

        orderSheets.stream().filter(orderSheet -> orderSheet.getCount() > 1 && orderSheet.getArbitraryBuilder() == null)
                .forEach(orderSheet -> result.put(orderSheet.getOrderClass(), fixtureMonkey.giveMe(orderSheet.getOrderClass(), orderSheet.getCount())));

        orderSheets.stream().filter(orderSheet -> orderSheet.getArbitraryBuilder() != null)
                .forEach(orderSheet -> result.put(orderSheet.getArbitraryBuilder().sample().getClass(), orderSheet.getArbitraryBuilder().sampleList(orderSheet.getCount())));

        return result;
    }

    protected <T> List<T> getOrderedObject(Class<T> targetClass) {
        return (List<T>) orderedObjectMap.get(targetClass);
    }

    protected <T> ArbitraryBuilder<T> orderCustom(Class<T> targetClass) {
        return fixtureMonkey.giveMeBuilder(targetClass);
    }

    protected <T> T getFixture(Class<T> targetClass) {
        return fixtureMonkey.giveMeOne(targetClass);
    }

    protected <T> List<T> getFixture(Class<T> targetClass, int count) {
        return fixtureMonkey.giveMe(targetClass, count);
    }

    protected <T> ArbitraryBuilder<T> getFixtureBuilder(Class<T> targetClass){
        return fixtureMonkey.giveMeBuilder(targetClass);
    }

    protected abstract List<OrderSheet> ordersObject();
}
