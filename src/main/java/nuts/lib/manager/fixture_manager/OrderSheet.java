package nuts.lib.manager.fixture_manager;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import lombok.Getter;

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

    static public OrderSheet order(Class<?> orderClass, int count) {
        return new OrderSheet(orderClass, count);
    }

    static public OrderSheet order(ArbitraryBuilder<?> arbitraryBuilder, int count) {
        return new OrderSheet(arbitraryBuilder,count);
    }
}
