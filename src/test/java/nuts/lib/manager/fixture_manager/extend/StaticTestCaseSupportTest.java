package nuts.lib.manager.fixture_manager.extend;

import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.lib.manager.fixture_manager.target.RequestObject;
import org.junit.jupiter.api.Test;

import java.util.List;

class StaticTestCaseSupportTest extends StaticTestCaseSupport {

    @Override
    protected List<OrderSheet> ordersObject() {
        return List.of(OrderSheet.order(orderCustom(RequestObject.class).set("adminName", "helloCustom"), 10));
    }

    @Test
    void test() {

        List<RequestObject> requestObjects = getOrderedObject(RequestObject.class);
        System.out.println(requestObjects);


    }
}