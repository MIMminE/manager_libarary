package nuts.lib.manager.fixture_manager;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import lombok.Getter;

/**
 * This is the DTO class used by the fixture creation order used by the {@link FixtureGenerateSupport#getOrderedObject(Class)}.
 *
 * @author nuts
 * @since 2024. 05. 23
 */
@Getter
public class OrderSheet {
    Class<?> orderClass;
    int count;
    ArbitraryBuilder<?> arbitraryBuilder;

    private OrderSheet(Class<?> orderClass, int count) {
        this.orderClass = orderClass;
        this.count = count;
    }

    private OrderSheet(ArbitraryBuilder<?> arbitraryBuilder, int count) {
        this.arbitraryBuilder = arbitraryBuilder;
        this.count = count;
    }

    /**
     * Create a fixture of the given class and return it as a list.
     *
     * <pre>
     * {@code
     * OrderSheet.order(Admin.class, 5)
     * }
     * </pre>
     */
    static public OrderSheet order(Class<?> orderClass, int count) {
        return new OrderSheet(orderClass, count);
    }

    /**
     * ArbitraryBuilder allows you to set rules for fixtures you want to create.
     * See <a href="https://naver.github.io/fixture-monkey/v1-0-0/docs/customizing-objects/apis/"> fixture_monkey_api</a>
     * <pre>
     * {@code
     *  OrderSheet.order(orderCustom(AdminEntity.class)
     *      .set("password", Arbitraries.strings().alpha().ofMaxLength(20)), 5)
     * }
     * </pre>
     */
    static public OrderSheet order(ArbitraryBuilder<?> arbitraryBuilder, int count) {
        return new OrderSheet(arbitraryBuilder, count);
    }
}
