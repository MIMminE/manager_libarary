package nuts.lib.manager.fixture_manager;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import com.navercorp.fixturemonkey.FixtureMonkey;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * It is an improvement from the existing method of inheritance.
 * <p>
 * The existing method was heavily loaded because it was initialized for each test.
 *
 * <pre>
 * {@code
 * static FixtureManager fixtureManager;
 *
 * @BeforeAll
 * static void setUp() {
 *    fixtureManager = new FixtureManager(List.of(
 *            OrderSheet.order(
 *                orderCustom(FixtureSampleClass.class)
 *                        .minSize("requestId", 3), 1)));
 * }
 * }
 * </pre>
 * It is important to note that from this version onwards, the validation annotation plugin has been modified to not be used.
 */
public class FixtureManager {

    private final Map<Class<?>, List<?>> orderObjectMap;
    private static final FixtureMonkey fixtureMonkey = FixtureMonkeySupplier.supplierFieldReflection.get();

    public FixtureManager(List<OrderSheet> orderSheets) {
        this.orderObjectMap = init(orderSheets);
    }

    public <T> T getOrderObject(Class<T> targetClass) {
        return (T) orderObjectMap.get(targetClass).get(0);
    }

    public <T> List<T> getOrderObjects(Class<T> targetClass) {
        return (List<T>) orderObjectMap.get(targetClass);
    }

    public static <T> ArbitraryBuilder<T> orderCustom(Class<T> targetClass) {
        return fixtureMonkey.giveMeBuilder(targetClass);
    }

    private Map<Class<?>, List<?>> init(List<OrderSheet> orderSheets) {
        Map<Class<?>, List<?>> result = new ConcurrentHashMap<>();

        for (OrderSheet orderSheet : orderSheets) {
            System.out.println(orderSheet.toString());
            if (orderSheet.getArbitraryBuilder() == null) {
                result.put(orderSheet.getOrderClass(), fixtureMonkey.giveMe(orderSheet.getOrderClass(), orderSheet.getCount()));
            } else {
                result.put(orderSheet.getArbitraryBuilder().sample().getClass(), orderSheet.getArbitraryBuilder().sampleList(orderSheet.getCount()));
            }
        }
        return result;
    }
}
