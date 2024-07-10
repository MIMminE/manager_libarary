package nuts.lib.manager.fixture_manager;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * By inheriting a test class, you can easily configure a test fixture.
 * <p>
 * Use Manual
 * <p>
 * step 1. Override the {@link FixtureGenerateSupport#ordersObject()} method to wrap the various fixtures needed for the test in an {@link OrderSheet} class and return them as a list.
 * <pre>
 * {@code
 *
 *
 * @Override
 * protected List<OrderSheet> ordersObject() {
 *     return List.of(
 *             OrderSheet.order(Admin.class, 5),
 *             OrderSheet.order(orderCustom(AdminEntity.class)
 *                  .set("password", Arbitraries.strings().alpha().ofMaxLength(20)), 5),
 *             OrderSheet.order(orderCustom(GroupEntity.class)
 *                  .set("secretKey", Arbitraries.strings().alpha().ofMinLength(4).ofMaxLength(8)), 3),
 *             OrderSheet.order(ApprovalEntity.class, 5));
 * }
 * }
 * </pre>
 * <p>
 * step 2. Returns a list of fixtures created using the {@link FixtureGenerateSupport#getOrderedObject(Class)}.
 *
 * <pre>
 * {@code
 * List<Admin> testAdmins = getOrderedObject(Admin.class);
 * List<AdminEntitySet> adminEntitiesSets =
 *      getOrderedObject(AdminEntity.class).stream().map(AdminEntitySet::new).toList();
 * List<GroupEntitySet> groupEntitiesSets =
 *      getOrderedObject(GroupEntity.class).stream().map(GroupEntitySet::new).toList();
 * }
 * </pre>
 * <p>
 * It can inherit the Spring Integration Test Support class to help you pre-set up the environment you need for your tests.
 *
 * <pre>
 * {@code
 * @SpringBootTest
 * @ActiveProfiles("test")
 * public class IntegrationTestSupport extends FixtureGenerateSupport {
 *
 *
 *     @Autowired
 *     protected AdminRepository adminRepository;
 *     @Autowired
 *     protected GroupRepository groupRepository;
 *     @Autowired
 *     protected ApprovalRepository approvalRepository;
 *     @Autowired
 *     protected AdminService adminService;
 *     @Autowired
 *     protected ApprovalService approvalService;
 *
 *     @Override
 *     protected List<OrderSheet> ordersObject() {
 *         return List.of(
 *                 OrderSheet.order(Admin.class, 5),
 *                 OrderSheet.order(orderCustom(AdminEntity.class)
 *                      .set("password", Arbitraries.strings().alpha().ofMaxLength(20)), 5),
 *                 OrderSheet.order(orderCustom(GroupEntity.class)
 *                      .set("secretKey", Arbitraries.strings().alpha().ofMinLength(4).ofMaxLength(8)), 3),
 *                 OrderSheet.order(ApprovalEntity.class, 5));
 *     }
 * }
 * [ 2024. 07. 09 update example ]
 *
 *
 * }
 *
 * </pre>
 *
 * @author nuts
 * @since 2024. 05. 23
 */

/**
 * [ Add example ]
 * <pre>
 * {@code
 * List.of(OrderSheet.order(orderCustom(CreateCorporationRequest.class).set("grade",
 *          Arbitraries.of("basic", "gold")), 1));
 * }
 * @since 2024. 07. 09
 * </pre>
 */
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

    protected <T> ArbitraryBuilder<T> getFixtureBuilder(Class<T> targetClass) {
        return fixtureMonkey.giveMeBuilder(targetClass);
    }

    public void printFixtures() {
        this.orderedObjectMap.entrySet().forEach(e -> System.out.println(e.getKey() + " " + e.getValue()));
    }

    public void changeFieldValue(Object instance, String fieldName, Object value) {
        try {
            Class<?> aClass = instance.getClass();
            Field field = aClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract List<OrderSheet> ordersObject();
}
